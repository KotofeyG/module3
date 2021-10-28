package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToDtoConverter implements Converter<Tag, TagDto> {
    @Override
    public TagDto convert(Tag source) {
        return TagDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}