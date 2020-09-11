package com.tictactoe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping()
    public ResponseEntity<?> createTicTacToeGame() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{ticTacToeId}/join")
    public ResponseEntity<?> joinTicTacToeGame() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{ticTacToeId}/start")
    public ResponseEntity<?> startGame() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{ticTacToeId}/move")
    public ResponseEntity<?> makeAMove() {
        return ResponseEntity.ok(null);
    }

}

