package com.tictactoe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class TicTacToeException extends ResponseStatusException {
    private String message;
    private HttpStatus httpStatus;
    private Object data = null;

    public TicTacToeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public TicTacToeException(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message);
        this.message = message;
        this.data = data;
    }

}
