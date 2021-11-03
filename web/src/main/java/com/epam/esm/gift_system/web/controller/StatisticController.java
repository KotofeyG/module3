package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.StatisticsService;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/popular-tag/rich-user")
    public List<TagDto> findMostPopularTag() {
        return statisticsService.findMostPopularTag();
    }
}