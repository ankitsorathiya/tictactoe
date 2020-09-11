package com.tictactoe.service.impl;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.service.IntelligenceMoveService;
import org.springframework.stereotype.Service;

@Service
public class IntelligenceMoveServiceImpl implements IntelligenceMoveService {

    @Override
    public Coordinate findBestPossibleMove(TicTacToe ticTacToe, Player player) {
        //1. understand the position of opponent, if any winning spot for opponent,block
        //2. build winning path
        return Coordinate.builder().build();
    }
}
