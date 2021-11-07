package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DtoToGiftCertificateConverterTest {
    @InjectMocks
    private DtoToGiftCertificateConverter toGiftCertificateConverter;
    @Mock
    private DtoToTagConverter dtoToTagConverter;

    private Tag tag;
    private TagDto tagDto;
    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;

    @BeforeEach
    public void setUp() {
        tag = Tag.builder().id(1L).name("FirstTag").build();
        tagDto = TagDto.builder().id(1L).name("FirstTag").build();
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("CertificateName")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagDtoList(List.of(tagDto))
                .build();
        certificate = GiftCertificate.builder()
                .id(1L)
                .name("CertificateName")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagList(Set.of(tag))
                .build();
    }

    @Test
    void convert() {
        doReturn(tag).when(dtoToTagConverter).convert(Mockito.any(TagDto.class));
        GiftCertificate actual = toGiftCertificateConverter.convert(certificateDto);
        assertEquals(certificate, actual);
    }
}