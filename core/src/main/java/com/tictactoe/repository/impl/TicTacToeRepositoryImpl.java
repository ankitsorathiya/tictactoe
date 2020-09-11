package com.tictactoe.repository.impl;

import com.tictactoe.model.TicTacToe;
import com.tictactoe.repository.TicTacToeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TicTacToeRepositoryImpl extends AbstractRepositoryImpl<TicTacToe> implements TicTacToeRepository {
    private final Collection<TicTacToe> ticTacToes = new ArrayList<>();

    @Override
    protected Collection<TicTacToe> getAllEntities() {
        return this.ticTacToes;
    }

    @Override
    protected int getId(TicTacToe entity) {
        return entity.getId();
    }

    @Override
    protected void setId(TicTacToe entity, Integer id) {
        entity.setId(id);
    }
}
