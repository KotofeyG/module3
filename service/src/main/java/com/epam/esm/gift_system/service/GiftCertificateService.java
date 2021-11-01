package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    List<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto);
}