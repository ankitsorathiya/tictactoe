package com.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Player {
    private Integer id;
    private String playerName;
    private char symbol;
    private boolean cpu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getSymbol() == player.getSymbol() &&
                isCpu() == player.isCpu() &&
                Objects.equals(getId(), player.getId()) &&
                Objects.equals(getPlayerName(), player.getPlayerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayerName(), getSymbol(), isCpu());
    }
}
