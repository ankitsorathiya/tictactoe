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
public class PlayerMoveCache {
    private Player player;
    private int[] rowPositions;
    private int[] colPositions;
    private int topLeftToBottomRight;
    private int bottomLeftToTopRight;
}
