package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class DtoToGiftCertificateAttributeConverterTest {
    private DtoToGiftCertificateAttributeConverter toGiftCertificateAttributeConverter;
    private GiftCertificateAttributeDto certificateAttributeDto;
    private GiftCertificateAttribute certificateAttribute;

    @BeforeEach
    public void setUp() {
        toGiftCertificateAttributeConverter = new DtoToGiftCertificateAttributeConverter();
        certificateAttributeDto = GiftCertificateAttributeDto.builder()
                .tagNameList(List.of("FirstTagName", "SecondTagName", "ThirdTagName", "FourTagName"))
                .searchPart("searchPart")
                .sortingFieldList(List.of("id", "name", "description", "price", "duration", "createDate", "lastUpdateDate"))
                .orderSort("desc")
                .build();
        certificateAttribute = GiftCertificateAttribute.builder()
                .tagNameList(List.of("FirstTagName", "SecondTagName", "ThirdTagName", "FourTagName"))
                .searchPart("searchPart")
                .sortingFieldList(List.of("id", "name", "description", "price", "duration", "createDate", "lastUpdateDate"))
                .orderSort("desc")
                .build();
    }

    @Test
    void convert() {
        GiftCertificateAttribute actual = toGiftCertificateAttributeConverter.convert(certificateAttributeDto);
        assertEquals(certificateAttribute, actual);
    }
}