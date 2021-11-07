package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;

public interface EntityConverterService {
    Tag convertDtoIntoEntity(TagDto tagDto);

    User convertDtoIntoEntity(UserDto userDto);

    GiftCertificate convertDtoIntoEntity(GiftCertificateDto certificateDto);

    GiftCertificateAttribute convertDtoIntoEntity(GiftCertificateAttributeDto certificateAttributeDto);
}