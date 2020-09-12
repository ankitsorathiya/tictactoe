package com.tictactoe.service.impl;

import com.tictactoe.dto.Coordinate;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.service.IntelligenceMoveService;
import com.tictactoe.service.TicTacToeGameMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IntelligenceMoveServiceImpl implements IntelligenceMoveService {
    @Autowired
    private TicTacToeGameMoveService ticTacToeGameMoveService;
    @Override
    public Coordinate findBestPossibleMove(TicTacToe ticTacToe, Player player) {
        //1. understand the position of opponent, if any winning spot for opponent,block
        //2. build winning path
        Random random = new Random();
        Coordinate coordinate = null;
        while (true) {
            int row = random.nextInt(ticTacToe.getTicTacToeBoard().getBoardSize());
            int col = random.nextInt(ticTacToe.getTicTacToeBoard().getBoardSize());
            coordinate = Coordinate.builder().row(row).col(col).build();
            boolean found = ticTacToeGameMoveService.isCoordinatesFree(ticTacToe, coordinate);
            if (found) {
                break;
            }
        }
        return coordinate;
    }
}
