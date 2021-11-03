package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificate findCertificateById(Long id);

    List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto);
}