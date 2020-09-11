package com.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicTacToe {
    private Integer id;
    private Player player1;
    private Player player2;
    private TicTacToeBoard ticTacToeBoard;
    private Player winner;
    private boolean draw;
    private GameStatus gameStatus;
    private Player whoseTurn;
}
