package com.epam.esm.gift_system.service;

import java.util.List;

public interface BaseService<T> {
    T create(T t);

    T update(Long id, T t);

    T findById(Long id);

    List<T> findAll();

    void delete(Long id);
}