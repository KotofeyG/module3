package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.StatisticsDao;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.StatisticsService;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsDao statisticsDao;
    private final DtoConverterService dtoConverter;

    @Autowired
    public StatisticsServiceImpl(StatisticsDao statisticsDao, DtoConverterService dtoConverter) {
        this.statisticsDao = statisticsDao;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public TagDto findMostPopularTag() {
        return statisticsDao.findMostPopularTag()
                .map(dtoConverter::convertEntityIntoDto)
                .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }
}