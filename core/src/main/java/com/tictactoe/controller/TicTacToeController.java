package com.tictactoe.controller;

import com.tictactoe.dto.Move;
import com.tictactoe.dto.MoveResult;
import com.tictactoe.dto.TicTacToeGameDTO;
import com.tictactoe.model.Player;
import com.tictactoe.model.TicTacToe;
import com.tictactoe.service.TicTacToeGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/v1/ticTacToe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Slf4j
public class TicTacToeController {
    @Autowired
    private TicTacToeGameService ticTacToeGameService;
    @PostMapping()
    public ResponseEntity<?> createTicTacToeGame(@RequestBody TicTacToeGameDTO ticTacToeGameDTO) {
        TicTacToe ticTacToe = ticTacToeGameService.createGame(ticTacToeGameDTO);
        return ResponseEntity.ok(ticTacToe);
    }

    @PostMapping("/{ticTacToeId}/join")
    public ResponseEntity<?> joinTicTacToeGame(@PathVariable("ticTacToeId") Integer ticTacToeId, @RequestBody Player player) {
        TicTacToe ticTacToe = ticTacToeGameService.join(ticTacToeId, player);
        return ResponseEntity.ok(ticTacToe);
    }

    @PostMapping("/{ticTacToeId}/start")
    public ResponseEntity<?> startGame(@PathVariable("ticTacToeId") Integer ticTacToeId) {
        TicTacToe ticTacToe = ticTacToeGameService.start(ticTacToeId);
        return ResponseEntity.ok(ticTacToe);
    }

    @PostMapping("/{ticTacToeId}/move")
    public ResponseEntity<?> makeAMove(@PathVariable("ticTacToeId") Integer ticTacToeId, @RequestBody Move move) {
        MoveResult moveResult = ticTacToeGameService.move(move);
        return ResponseEntity.ok(moveResult);
    }

    @PostMapping("/{ticTacToeId}/cpu/move")
    public ResponseEntity<?> makeAMove(@PathVariable("ticTacToeId") Integer ticTacToeId, @RequestBody Player player) {
        MoveResult moveResult = ticTacToeGameService.cpuMove(ticTacToeId, player);
        return ResponseEntity.ok(moveResult);
    }

}

