package com.tictactoe.service.impl;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.dto.TicTacToeGameDTO;
import com.tictactoe.exception.TicTacToeException;
import com.tictactoe.model.GameStatus;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.service.AutoPlayerTicTacToeService;
import com.tictactoe.service.TicTacToeGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Slf4j
@Service
public class AutoPlayerTicTacToeServiceImpl implements AutoPlayerTicTacToeService {

    @Autowired
    private TicTacToeGameService ticTacToeGameService;

    public void roleTheGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Player1 name:");
        String player1Name = scanner.next();
        System.out.println("Do you want to play with cpu? (Y/N)");
        String playWithCpuText = scanner.next();
        boolean playWithCpu = playWithCpuText.equalsIgnoreCase("y");
        String player2Name = null;
        if (!playWithCpu) {
            System.out.println("Enter Player2 name:");
            player2Name = scanner.next();
        }
        System.out.println("Enter board size?");
        int boardSize = scanner.nextInt();
        TicTacToeGameDTO ticTacToeGameDTO = TicTacToeGameDTO.builder()
                .boardSize(boardSize)
                .player(Player.builder().playerName(player1Name).symbol('x').build())
                .playWithCpu(playWithCpu).build();

        TicTacToe ticTacToe = ticTacToeGameService.createGame(ticTacToeGameDTO);
        if (!playWithCpu) {
            Player player2 = Player.builder().playerName(player2Name).symbol('o').build();
            ticTacToe = ticTacToeGameService.join(ticTacToe.getId(), player2);
        }
        ticTacToe = ticTacToeGameService.start(ticTacToe.getId());
        System.out.println(ticTacToe.getTicTacToeBoard().toString());
        while (true) {
            try {
                if (ticTacToe.getGameStatus().equals(GameStatus.FINISHED)) {
                    break;
                }
                MoveResult moveResult = null;
                if (!ticTacToe.getWhoseTurn().isCpu()) {
                    System.out.println(ticTacToe.getWhoseTurn().getPlayerName() + " enter row:");
                    int row = scanner.nextInt();
                    System.out.println(ticTacToe.getWhoseTurn().getPlayerName() + "enter col:");
                    int col = scanner.nextInt();
                    Coordinate coordinate = Coordinate.builder().row(row).col(col).build();
                    moveResult = ticTacToeGameService.move(Move.builder().player(ticTacToe.getWhoseTurn())
                            .coordinate(coordinate)
                            .gameId(ticTacToe.getId())
                            .build());
                } else {
                    moveResult = ticTacToeGameService.move(Move.builder().gameId(ticTacToe.getId()).player(ticTacToe.getWhoseTurn()).build());
                }
                if (!moveResult.isMoved()) {
                    log.info("invalid move");
                }
                if (moveResult.isWinningMove()) {
                    log.info("{} You won!", moveResult.getWinner().getPlayerName());
                    break;
                }
                if (moveResult.isDraw()) {
                    log.info("it's tie");
                    break;
                }
            } catch (TicTacToeException ticTacToeException) {
                System.out.println(ticTacToe.getTicTacToeBoard());
                log.error("{}", ticTacToeException.getMessage());
            }
        }

    }
}
