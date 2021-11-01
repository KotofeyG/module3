package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateToDtoConverter implements Converter<GiftCertificate, GiftCertificateDto> {
    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public GiftCertificateToDtoConverter(TagToDtoConverter tagToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate source) {
        return GiftCertificateDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tagDtoList(source.getTagList().stream().map(tagToDtoConverter::convert).toList())
                .build();
    }
}