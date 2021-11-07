package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificate findCertificateById(Long id);

    CustomPage<GiftCertificateDto> findByAttributes(GiftCertificateAttributeDto attributeDto, CustomPageable pageable);
}