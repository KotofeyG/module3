package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto create(TagDto tagDto);

    TagDto findById(Long id);

    List<TagDto> findAll();

    void delete(Long id);
}