package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToGiftCertificateAttributeConverter implements Converter<GiftCertificateAttributeDto, GiftCertificateAttribute> {
    @Override
    public GiftCertificateAttribute convert(GiftCertificateAttributeDto source) {
        return GiftCertificateAttribute.builder()
                .tagNameList(source.getTagNameList())
                .searchPart(source.getSearchPart())
                .orderSort(source.getOrderSort())
                .sortingFieldList(source.getSortingFieldList())
                .build();
    }
}