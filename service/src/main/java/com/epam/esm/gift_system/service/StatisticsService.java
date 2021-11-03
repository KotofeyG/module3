package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.TagDto;

import java.util.List;

public interface StatisticsService {
    List<TagDto> findMostPopularTag();
}