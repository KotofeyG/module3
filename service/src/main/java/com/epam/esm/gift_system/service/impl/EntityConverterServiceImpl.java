package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.converter.DtoToGiftCertificateAttributeConverter;
import com.epam.esm.gift_system.service.converter.DtoToGiftCertificateConverter;
import com.epam.esm.gift_system.service.converter.DtoToTagConverter;
import com.epam.esm.gift_system.service.converter.DtoToUserConverter;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityConverterServiceImpl implements EntityConverterService {
    private final DtoToTagConverter toTagConverter;
    private final DtoToUserConverter toUserConverter;
    private final DtoToGiftCertificateConverter toCertificateConverter;
    private final DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter;

    @Autowired
    public EntityConverterServiceImpl(DtoToTagConverter toTagConverter, DtoToUserConverter toUserConverter
            , DtoToGiftCertificateConverter toCertificateConverter
            , DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter) {
        this.toTagConverter = toTagConverter;
        this.toUserConverter = toUserConverter;
        this.toCertificateConverter = toCertificateConverter;
        this.toCertificateAttributeConverter = toCertificateAttributeConverter;
    }

    @Override
    public Tag convertDtoIntoEntity(TagDto tagDto) {
        return toTagConverter.convert(tagDto);
    }

    @Override
    public User convertDtoIntoEntity(UserDto userDto) {
        return toUserConverter.convert(userDto);
    }

    @Override
    public GiftCertificate convertDtoIntoEntity(GiftCertificateDto certificateDto) {
        return toCertificateConverter.convert(certificateDto);
    }

    @Override
    public GiftCertificateAttribute convertDtoIntoEntity(GiftCertificateAttributeDto certificateAttributeDto) {
        return toCertificateAttributeConverter.convert(certificateAttributeDto);
    }
}