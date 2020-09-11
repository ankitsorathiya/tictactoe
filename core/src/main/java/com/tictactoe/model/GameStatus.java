package com.tictactoe.model;

public enum GameStatus {
    CREATED(0), JOINED(1), STARTED(2), FINISHED(3);
    private final int priority;

    GameStatus(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
