package com.tictactoe.service;

import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.dto.TicTacToeGameDTO;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.model.TicTacToeBoard;

public interface TicTacToeGameService {
    TicTacToe findGame(int gameId);

    TicTacToe createGame(TicTacToeGameDTO ticTacToeGameDTO);

    TicTacToe join(int gameId, Player player);

    TicTacToe start(int gameId);

    TicTacToeBoard findBoard(int gameId);

    MoveResult move(Move move);

    MoveResult cpuMove(int gameId, Player player);
}
