package com.tictactoe;

import com.tictactoe.dto.TicTacToeGameDTO;
import com.tictactoe.exception.TicTacToeException;
import com.tictactoe.model.Player;
import com.tictactoe.repository.PlayerRepository;
import com.tictactoe.repository.TicTacToeRepository;
import com.tictactoe.service.IntelligenceMoveService;
import com.tictactoe.service.TicTacToeGameMoveService;
import com.tictactoe.service.TicTacToeGameService;
import com.tictactoe.service.impl.TicTacToeGameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.internal.util.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeGameTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TicTacToeRepository ticTacToeRepository;
    @Mock
    private TicTacToeGameMoveService ticTacToeGameMoveService;
    @Mock
    private IntelligenceMoveService intelligenceMoveService;

    @InjectMocks
    private TicTacToeGameService ticTacToeGameService = new TicTacToeGameServiceImpl();


    @Test
    public void initializationTest() {
        Assert.notNull(ticTacToeGameService);
        Assert.notNull(ticTacToeGameMoveService);
        Assert.notNull(playerRepository);
        Assert.notNull(ticTacToeRepository);
        Assert.notNull(intelligenceMoveService);
    }

    @Test(expected = TicTacToeException.class)
    public void failGameCreate() {
        TicTacToeGameDTO ticTacToeGameDTO = TicTacToeGameDTO.builder()
                .boardSize(-1)
                .player(Player.builder().playerName("ankit").symbol('x').build())
                .playWithCpu(false).build();
        ticTacToeGameService.createGame(ticTacToeGameDTO);
    }

//    @Test()
//    public void validGame() {
//        TicTacToeGameDTO ticTacToeGameDTO = TicTacToeGameDTO.builder()
//                .boardSize(2)
//                .player(Player.builder().playerName("ankit").symbol('x').build())
//                .playWithCpu(true).build();
//        TicTacToe ticTacToe = ticTacToeGameService.createGame(ticTacToeGameDTO);
//        Assert.notNull(ticTacToe);
//        Assert.isTrue(ticTacToe.getGameStatus().equals(GameStatus.CREATED));
//    }
}