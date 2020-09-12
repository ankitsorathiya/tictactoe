package com.tictactoe.dto;

import com.tictactoe.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicTacToeGameDTO {
    private Player player;
    private int boardSize;//n*n
    private boolean playWithCpu;
}
