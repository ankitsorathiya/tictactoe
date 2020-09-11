package com.tictactoe.service;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;

public interface IntelligenceMoveService {
    Coordinate findBestPossibleMove(TicTacToe ticTacToe, Player player);
}
