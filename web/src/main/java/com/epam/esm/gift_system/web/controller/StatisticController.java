package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.StatisticsService;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.web.hateaos.HateaosBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {
    private final StatisticsService statisticsService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public StatisticController(StatisticsService statisticsService, HateaosBuilder hateaosBuilder) {
        this.statisticsService = statisticsService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @GetMapping("/popular-tag/rich-user")
    public TagDto findMostPopularTag() {
        TagDto tagDto = statisticsService.findMostPopularTag();
        hateaosBuilder.setLinks(tagDto);
        return tagDto;
    }
}