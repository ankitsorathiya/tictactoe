package com.tictactoe.service.impl;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.exception.TicTacToeException;
import com.tictactoe.model.PlayerMoveCache;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.model.TicTacToeBoard;
import com.tictactoe.service.TicTacToeGameMoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicTacToeGameMoveServiceImpl implements TicTacToeGameMoveService {

    @Override
    public MoveResult move(TicTacToe ticTacToe, Move move) {
        if (!ticTacToe.getWhoseTurn().getId().equals(move.getPlayer().getId())) {
            log.error("it isn't your turn");
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "It's not your turn");
        }
        boolean free = isCoordinatesFree(ticTacToe, move.getCoordinate());
        if (!free) {
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "position already occupied");
        }
        Coordinate coordinate = move.getCoordinate();
        TicTacToeBoard ticTacToeBoard = ticTacToe.getTicTacToeBoard();
        ticTacToeBoard.getBoard()[coordinate.getRow()][coordinate.getCol()] = move.getPlayer().getSymbol();
        PlayerMoveCache playerMoveCache = ticTacToeBoard.getPlayerMoveCaches().stream().filter(cache -> cache.getPlayer().getId().equals(move.getPlayer().getId())).findFirst().get();

        playerMoveCache.getRowPositions()[coordinate.getRow()] = playerMoveCache.getRowPositions()[coordinate.getRow()] + 1;
        MoveResult.MoveResultBuilder moveResultBuilder = MoveResult.builder()
                .moved(true);
        if (playerMoveCache.getRowPositions()[coordinate.getRow()] == ticTacToeBoard.getBoardSize()) {
            moveResultBuilder.winningMove(true)
                    .winner(move.getPlayer());
        }

        playerMoveCache.getColPositions()[coordinate.getCol()] = playerMoveCache.getColPositions()[coordinate.getCol()] + 1;
        if (playerMoveCache.getColPositions()[coordinate.getCol()] == ticTacToeBoard.getBoardSize()) {
            moveResultBuilder.winningMove(true)
                    .winner(move.getPlayer());
        }

        if (isTopLeftToBottomRightDiagonal(coordinate)) {
            playerMoveCache.setTopLeftToBottomRight(playerMoveCache.getTopLeftToBottomRight() + 1);
            if (playerMoveCache.getTopLeftToBottomRight() == ticTacToeBoard.getBoardSize()) {
                moveResultBuilder.winningMove(true)
                        .winner(move.getPlayer());
            }
        }

        if (isBottomLeftToTopRightDiagonal(ticTacToeBoard.getBoardSize(), coordinate)) {
            playerMoveCache.setBottomLeftToTopRight(playerMoveCache.getBottomLeftToTopRight() + 1);
            if (playerMoveCache.getBottomLeftToTopRight() == ticTacToeBoard.getBoardSize()) {
                moveResultBuilder.winningMove(true)
                        .winner(move.getPlayer());
            }
        }
        ticTacToeBoard.setMovesPlayed(ticTacToeBoard.getMovesPlayed() + 1);
        if (ticTacToeBoard.getMovesPlayed() == ticTacToeBoard.getBoardSize() * ticTacToeBoard.getBoardSize()) {
            moveResultBuilder.draw(true);
        }
        System.out.println(ticTacToeBoard.toString());
        return moveResultBuilder.build();
    }

    private boolean isTopLeftToBottomRightDiagonal(Coordinate coordinate) {
        return (coordinate.getRow() == coordinate.getCol());
    }

    private boolean isBottomLeftToTopRightDiagonal(int boardSize, Coordinate coordinate) {
        int minRow = 0, maxCol = boardSize - 1;
        //if it requires same number to be reduced in row as well as column than it's bottomLeftToTopRightDiagonal
        return Math.abs(minRow - coordinate.getRow()) == (maxCol - coordinate.getCol());
    }

    @Override
    public boolean isCoordinatesFree(TicTacToe ticTacToe, Coordinate coordinate) {
        //coordinates are 0 based
        if (coordinate.getRow() < 0 || coordinate.getCol() < 0) {
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "negative coordinates");
        }
        if (coordinate.getCol() >= ticTacToe.getTicTacToeBoard().getBoardSize() || coordinate.getRow() >= ticTacToe.getTicTacToeBoard().getBoardSize()) {
            throw new TicTacToeException(HttpStatus.BAD_REQUEST, "invalid position");
        }
        Character[][] board = ticTacToe.getTicTacToeBoard().getBoard();
        Character value = board[coordinate.getRow()][coordinate.getCol()];
        return value == null;
    }
}
