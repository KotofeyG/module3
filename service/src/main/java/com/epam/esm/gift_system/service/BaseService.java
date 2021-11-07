package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;

public interface BaseService<T> {
    T create(T t);

    T update(Long id, T t);

    T findById(Long id);

    CustomPage<T> findAll(CustomPageable pageable);

    void delete(Long id);

    default int calculateOffset(CustomPageable pageable) {
        return pageable.getSize() * pageable.getPage();
    }
}