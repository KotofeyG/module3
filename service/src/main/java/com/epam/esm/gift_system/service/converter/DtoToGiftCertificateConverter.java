package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
public class DtoToGiftCertificateConverter implements Converter<GiftCertificateDto, GiftCertificate> {
    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public DtoToGiftCertificateConverter(DtoToTagConverter dtoToTagConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public GiftCertificate convert(GiftCertificateDto source) {
        return GiftCertificate.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tagList(source.getTagDtoList().stream()
                        .map(dtoToTagConverter::convert)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }
}