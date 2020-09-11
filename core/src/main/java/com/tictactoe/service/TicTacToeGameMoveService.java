package com.tictactoe.service;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.model.TicTacToe;

public interface TicTacToeGameMoveService {
    MoveResult move(TicTacToe ticTacToe, Move move);

    boolean isCoordinatesFree(TicTacToe ticTacToe, Coordinate coordinate);

}
