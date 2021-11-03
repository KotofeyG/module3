package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Tag;

import java.util.List;

public interface StatisticsDao {
    List<Tag> findMostPopularTag();
}