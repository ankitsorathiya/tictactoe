package com.tictactoe.service.impl;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.dto.TicTacToeGameDTO;
import com.tictactoe.exception.TicTacToeException;
import com.tictactoe.model.GameStatus;
import com.tictactoe.model.Player;
import com.tictactoe.model.PlayerMoveCache;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.model.TicTacToeBoard;
import com.tictactoe.repository.PlayerRepository;
import com.tictactoe.repository.TicTacToeRepository;
import com.tictactoe.service.IntelligenceMoveService;
import com.tictactoe.service.TicTacToeGameMoveService;
import com.tictactoe.service.TicTacToeGameService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Setter
public class TicTacToeGameServiceImpl implements TicTacToeGameService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TicTacToeRepository ticTacToeRepository;
    @Autowired
    private TicTacToeGameMoveService ticTacToeGameMoveService;
    @Autowired
    private IntelligenceMoveService intelligenceMoveService;

    @Override
    public TicTacToe findGame(int gameId) {
        TicTacToe ticTacToe = ticTacToeRepository.findById(gameId);
        if (Objects.isNull(ticTacToe)) {
            log.error("no game found for id {}", gameId);
            throw new TicTacToeException(HttpStatus.NOT_FOUND, "There no such game exists.");
        }
        return ticTacToe;
    }

    @Override
    public TicTacToe createGame(TicTacToeGameDTO ticTacToeGameDTO) {
        if (ticTacToeGameDTO.getBoardSize() <= 0) {
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "Invalid board size");
        }
        Player player1 = ticTacToeGameDTO.getPlayer();
        if (Objects.isNull(player1.getId()) || player1.getId().intValue() == 0) {
            player1 = playerRepository.create(ticTacToeGameDTO.getPlayer());
        }
        Player player2 = null;
        if (ticTacToeGameDTO.isPlayWithCpu()) {
            player2 = playerRepository.create(Player.builder()
                    .cpu(true)
                    .symbol('o')
                    .playerName("Cpu playing with" + player1.getPlayerName()).build());
        }
        GameStatus gameStatus = GameStatus.CREATED;
        if (Objects.nonNull(player1) && Objects.nonNull(player2)) {
            //since playing with the CPU both the members have joined.
            gameStatus = gameStatus.JOINED;
        }

        PlayerMoveCache player1MoveCache = buildBoardCache(ticTacToeGameDTO.getBoardSize(), player1);
        PlayerMoveCache player2MoveCache = buildBoardCache(ticTacToeGameDTO.getBoardSize(), player2);
        Collection<PlayerMoveCache> playerMoveMoveCaches = Arrays.asList(player1MoveCache, player2MoveCache);
        Character[][] board = new Character[ticTacToeGameDTO.getBoardSize()][ticTacToeGameDTO.getBoardSize()];
        TicTacToeBoard ticTacToeBoard = TicTacToeBoard.builder()
                .board(board)
                .boardSize(ticTacToeGameDTO.getBoardSize())
                .playerMoveCaches(playerMoveMoveCaches)
                .build();
        TicTacToe ticTacToe = TicTacToe.builder()
                .player1(player1)
                .player2(player2)
                .gameStatus(gameStatus)
                .ticTacToeBoard(ticTacToeBoard)
                .build();
        ticTacToe = ticTacToeRepository.create(ticTacToe);
        return ticTacToe;
    }

    private PlayerMoveCache buildBoardCache(int boardSize, Player player) {
        return PlayerMoveCache.builder()
                .player(player)//second player can be null, he/she will be joining later
                .colPositions(new int[boardSize])
                .rowPositions(new int[boardSize])
                .build();
    }

    @Override
    public TicTacToe join(int gameId, Player player) {
        if (Objects.isNull(player.getId()) || player.getId().intValue() == 0) {
            player = playerRepository.create(player);
        }
        TicTacToe ticTacToe = findGame(gameId);
        if (ticTacToe.getGameStatus().getPriority() >= GameStatus.JOINED.getPriority()) {
            log.error("player {} is trying to join game {} but it's already in stage {}", player, gameId, ticTacToe.getGameStatus());
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "The game is already in " + ticTacToe.getGameStatus().name());
        }
        if (Objects.nonNull(ticTacToe.getPlayer2())) {
            log.error("player {} is trying to join game {} but other player has joined it", player, gameId, ticTacToe.getGameStatus());
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "other player has already joined the game");
        }
        ticTacToe.setPlayer2(player);
        //assigning the player cache
        Optional<PlayerMoveCache> playerMoveCacheOptional = ticTacToe.getTicTacToeBoard().getPlayerMoveCaches().stream().filter(playerMoveCache -> Objects.isNull(playerMoveCache.getPlayer())).findFirst();
        playerMoveCacheOptional.get().setPlayer(player);
        ticTacToe.setGameStatus(GameStatus.JOINED);
        return ticTacToe;
    }

    @Override
    public TicTacToe start(int gameId) {
        TicTacToe ticTacToe = findGame(gameId);
        if (!ticTacToe.getGameStatus().equals(GameStatus.JOINED)) {
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "invalid game stage " + ticTacToe.getGameStatus().name() + ", can not start");
        }
        ticTacToe.setGameStatus(GameStatus.STARTED);
        Player whoseTurn = ticTacToe.getPlayer1();
        //assumption, always giving opportunity to the first player who created the game. can be randomized
        ticTacToe.setWhoseTurn(whoseTurn);
        return ticTacToe;
    }

    @Override
    public MoveResult move(Move move) {
        TicTacToe ticTacToe = findGame(move.getGameId());
        if (!ticTacToe.getGameStatus().equals(GameStatus.STARTED)) {
            log.error("invalid game stage {} can't make move {}", ticTacToe.getGameStatus().name(), move);
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "invalid game stage " + ticTacToe.getGameStatus().name() + ", can not mark move");
        }
        if (move.getPlayer().isCpu()) {
            Coordinate coordinate = intelligenceMoveService.findBestPossibleMove(ticTacToe, move.getPlayer());
            move.setCoordinate(coordinate);
        }
        MoveResult moveResult = ticTacToeGameMoveService.move(ticTacToe, move);
        if (moveResult.isWinningMove()) {
            ticTacToe.setGameStatus(GameStatus.FINISHED);
            ticTacToe.setWinner(move.getPlayer());
            return moveResult;
        }
        if (moveResult.isMoved()) {
            Player whoseTurn = ticTacToe.getPlayer1().equals(ticTacToe.getWhoseTurn()) ? ticTacToe.getPlayer2() : ticTacToe.getPlayer1();
            ticTacToe.setWhoseTurn(whoseTurn);
            //keeping it here, if integrated as api need to move separetly
            if (whoseTurn.isCpu()) {
                Coordinate coordinate = intelligenceMoveService.findBestPossibleMove(ticTacToe, whoseTurn);
                this.move(Move.builder().coordinate(coordinate).gameId(move.getGameId()).player(whoseTurn).build());
            }
        }
        return moveResult;
    }

    @Override
    public TicTacToeBoard findBoard(int gameId) {
        return this.findGame(gameId).getTicTacToeBoard();
    }

    @Override
    @Deprecated
    public MoveResult cpuMove(int gameId, Player player) {
        TicTacToe ticTacToe = findGame(gameId);
        Coordinate coordinate = intelligenceMoveService.findBestPossibleMove(ticTacToe, player);
        Move move = Move.builder().coordinate(coordinate).player(player).gameId(gameId).build();
        return this.move(move);
    }

}
