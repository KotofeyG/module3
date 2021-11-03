package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Tag;

import java.util.Optional;

public interface TagDao extends BaseDao<Tag>{
    Optional<Tag> findByName(String name);

    boolean isTagUsedInCertificates(Long id);
}