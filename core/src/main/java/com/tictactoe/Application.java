package com.tictactoe;


import com.tictactoe.service.AutoPlayerTicTacToeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;


@SpringBootApplication
@ComponentScan({"com.tictactoe"})
@Slf4j
public class Application {
    @Autowired
    private AutoPlayerTicTacToeService autoPlayerTicTacToeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void play() {
        autoPlayerTicTacToeService.roleTheGame();
    }

}

