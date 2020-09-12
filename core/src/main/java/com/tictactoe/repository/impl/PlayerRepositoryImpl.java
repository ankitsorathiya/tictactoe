package com.tictactoe.repository.impl;

import com.tictactoe.model.Player;
import com.tictactoe.repository.PlayerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class PlayerRepositoryImpl extends AbstractRepositoryImpl<Player> implements PlayerRepository {
    private final Collection<Player> players = new ArrayList<>();

    @Override
    protected Collection<Player> getAllEntities() {
        return players;
    }

    @Override
    protected int getId(Player entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Player entity, Integer id) {
        entity.setId(id);
    }
}
