package com.tictactoe.repository;

import java.util.Collection;

public interface AbstractRepository<E> {
    Collection<E> findAll();

    E findById(Integer id);

    Collection<E> createAll(Collection<E> entities);

    E create(E entity);

}
