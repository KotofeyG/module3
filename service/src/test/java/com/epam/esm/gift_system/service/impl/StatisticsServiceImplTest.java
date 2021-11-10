package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.StatisticsDao;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {
    @InjectMocks
    private StatisticsServiceImpl service;
    @Mock
    private StatisticsDao statisticsDao;
    @Mock
    private DtoConverterService dtoConverter;

    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("TagName").build();
        tagDto = TagDto.builder().id(1L).name("TagName").build();
    }

    @Test
    void findMostPopularTag() {
        doReturn(Optional.of(tag)).when(statisticsDao).findMostPopularTag();
        doReturn(tagDto).when(dtoConverter).convertEntityIntoDto(any(Tag.class));
        TagDto actual = service.findMostPopularTag();
        assertEquals(tagDto, actual);
    }

    @Test
    void findMostPopularTagThrowsExceptionWithEmptyOptionalTag() {
        doReturn(Optional.empty()).when(statisticsDao).findMostPopularTag();
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findMostPopularTag());
        assertEquals(40410, thrown.getErrorCode());
    }
}