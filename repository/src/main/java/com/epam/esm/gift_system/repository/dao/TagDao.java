package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Tag;

import java.util.List;

public interface TagDao extends BaseDao<Tag>{
    Tag findOrCreateTag(Tag tag);

    List<Tag> findAll();

    boolean isNotExisting(String name);

    boolean isUsed(Long id);
}