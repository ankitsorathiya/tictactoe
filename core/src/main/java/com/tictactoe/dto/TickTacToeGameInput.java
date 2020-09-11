package com.tictactoe.dto;

import com.tictactoe.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickTacToeGameInput {
    private Player player;
    private int boardSize;//n*n
    private boolean playWithCpu;
}
