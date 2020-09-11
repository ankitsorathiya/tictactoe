package com.tictactoe.dto;

import com.tictactoe.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Move {
    private Player player;
    private int gameId;
    private Coordinate coordinate;
}
