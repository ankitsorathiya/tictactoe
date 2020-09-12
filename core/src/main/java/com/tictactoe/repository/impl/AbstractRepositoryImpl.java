package com.tictactoe.repository.impl;

import com.tictactoe.repository.AbstractRepository;

import java.util.Collection;
import java.util.Optional;


public abstract class AbstractRepositoryImpl<E> implements AbstractRepository<E> {
    protected abstract Collection<E> getAllEntities();

    protected abstract int getId(E entity);

    protected abstract void setId(E entity, Integer id);

    @Override
    public Collection<E> findAll() {
        return getAllEntities();
    }

    @Override
    public E findById(int id) {
        Optional<E> optionalE = getAllEntities().stream().filter(entity -> id == getId(entity)).findFirst();
        if (optionalE.isPresent()) {
            return optionalE.get();
        }
        return null;
    }

    @Override
    public Collection<E> createAll(Collection<E> entities) {
        Collection<E> existingEntities = getAllEntities();
        int id = getAllEntities().size() + 1; // getting new Id
        for (E entity : entities) {
            setId(entity, id);
            id++;
        }
        existingEntities.addAll(entities);
        return entities;
    }

    @Override
    public E create(E entity) {
        Collection<E> existingEntities = getAllEntities();
        int id = existingEntities.size() + 1;// getting new Id
        this.setId(entity, id);
        existingEntities.add(entity);
        return entity;
    }
}
