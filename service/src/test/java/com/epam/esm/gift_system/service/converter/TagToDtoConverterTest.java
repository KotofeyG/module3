package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagToDtoConverterTest {
    private TagToDtoConverter tagToDtoConverter;

    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    public void setUp() {
        tagToDtoConverter = new TagToDtoConverter();
        tag = Tag.builder().id(1L).name("TagName").build();
        tagDto = TagDto.builder().id(1L).name("TagName").build();
    }


    @Test
    void convert() {
        TagDto actual = tagToDtoConverter.convert(tag);
        assertEquals(tagDto, actual);
    }
}