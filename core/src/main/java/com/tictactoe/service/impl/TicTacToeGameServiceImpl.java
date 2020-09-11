package com.tictactoe.service.impl;

import com.tictactoe.dto.TickTacToeGameInput;
import com.tictactoe.model.GameStatus;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.model.TicTacToeBoard;
import com.tictactoe.repository.PlayerRepository;
import com.tictactoe.repository.TicTacToeRepository;
import com.tictactoe.service.TicTacToeGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TicTacToeGameServiceImpl implements TicTacToeGameService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TicTacToeRepository ticTacToeRepository;

    @Override
    public TicTacToe createGame(TickTacToeGameInput tickTacToeGameInput) {
        Player player1 = tickTacToeGameInput.getPlayer();
        if (Objects.isNull(player1.getId()) || player1.getId().intValue() == 0) {
            player1 = playerRepository.create(tickTacToeGameInput.getPlayer());
        }
        Player player2 = null;
        if (tickTacToeGameInput.isPlayWithCpu()) {
            player2 = playerRepository.create(Player.builder().playerName("Cpu playing with" + player1.getPlayerName()).build());
        }
        Player[][] positions = new Player[tickTacToeGameInput.getBoardSize()][tickTacToeGameInput.getBoardSize()];
        TicTacToeBoard ticTacToeBoard = TicTacToeBoard.builder()
                .positions(positions)
                .build();
        TicTacToe ticTacToe = TicTacToe.builder()
                .gameStatus(GameStatus.CREATED)
                .player1(player1)
                .player2(player2)
                .build();
        return null;
    }
}
