package com.tictactoe.service;

import com.tictactoe.dto.TickTacToeGameInput;
import com.tictactoe.model.TicTacToe;

public interface TicTacToeGameService {
    TicTacToe createGame(TickTacToeGameInput tickTacToeGameInput);
}
