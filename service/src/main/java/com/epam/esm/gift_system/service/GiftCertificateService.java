package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto create(GiftCertificateDto certificateDto);

    GiftCertificateDto update(Long id, GiftCertificateDto certificateDto);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto);

    void delete(Long id);
}