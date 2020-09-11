package com.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicTacToeBoard {
    private Player[][] positions;
    private Collection<PlayerMoveCache> playerMoveCache;
}
