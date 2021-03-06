package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Tag;

import java.util.Optional;

public interface StatisticsDao {
    Optional<Tag> findMostPopularTag();
}