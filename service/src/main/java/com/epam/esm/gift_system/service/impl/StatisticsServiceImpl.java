package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.StatisticsDao;
import com.epam.esm.gift_system.service.ConverterService;
import com.epam.esm.gift_system.service.StatisticsService;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsDao statisticsDao;
    private final ConverterService converter;

    @Autowired
    public StatisticsServiceImpl(StatisticsDao statisticsDao, ConverterService converter) {
        this.statisticsDao = statisticsDao;
        this.converter = converter;
    }

    @Override
    public TagDto findMostPopularTag() {
        return converter.convertEntityIntoDto(statisticsDao.findMostPopularTag());
    }
}