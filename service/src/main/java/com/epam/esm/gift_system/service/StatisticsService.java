package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.TagDto;

public interface StatisticsService {
    TagDto findMostPopularTag();
}