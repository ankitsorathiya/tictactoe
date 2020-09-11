package com.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicTacToeBoard {
    //can replace with actual player[][],just for simplicity taken char [][]
    private Character[][] board;
    private int boardSize;
    private int movesPlayed;
    private Collection<PlayerMoveCache> playerMoveCaches;

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < boardSize; row++) {
            for (int col = boardSize; col < boardSize; col++) {
                boardString.append(board[row][col] == null ? "|_|" : "|" + board[row][col] + "|");
            }
            boardString.append("\n");
        }
        return board.toString();
    }
}
