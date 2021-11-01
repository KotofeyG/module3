package com.epam.esm.gift_system.repository.dao;

import com.epam.esm.gift_system.repository.model.Tag;

public interface StatisticsDao {
    Tag findMostPopularTag();
}