package com.tictactoe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice(basePackages = {"com.tictactoe"})
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(TicTacToeException.class)
    public ResponseEntity<Object> customerException(TicTacToeException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        errors.put("code", ex.getCode());
        if (Objects.nonNull(ex.getData())) {
            errors.put("data", ex.getData());
        }
        HttpStatus status = ex.getHttpStatus();
        if (Objects.isNull(status)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errors, status);
    }
}
