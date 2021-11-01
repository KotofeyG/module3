package com.epam.esm.gift_system.repository.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    T create(T t);

    void update(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    void delete(T entity);
}