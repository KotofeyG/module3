package com.epam.esm.gift_system.repository.dao;

import java.util.Optional;

public interface BaseDao<T> {
    T create(T t);

    Optional<T> findById(Long id);

    void delete(T entity);
}