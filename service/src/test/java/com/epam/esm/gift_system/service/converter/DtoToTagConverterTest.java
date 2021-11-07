package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtoToTagConverterTest {
    private DtoToTagConverter toTagConverter;

    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    public void setUp() {
        toTagConverter = new DtoToTagConverter();
        tag = Tag.builder().id(1L).name("TagName").build();
        tagDto = TagDto.builder().id(1L).name("TagName").build();
    }

    @Test
    void convert() {
        Tag actual = toTagConverter.convert(tagDto);
        assertEquals(tag, actual);
    }
}