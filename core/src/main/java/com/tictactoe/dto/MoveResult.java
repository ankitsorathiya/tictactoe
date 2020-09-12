package com.tictactoe.dto;

import com.tictactoe.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MoveResult {
    private boolean moved;
    private boolean winningMove;
    private boolean draw;
    private Player winner;
    private Coordinate coordinate;// for cpu user this will be returned.
}
