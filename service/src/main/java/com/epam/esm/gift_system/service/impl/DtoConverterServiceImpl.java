package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.converter.GiftCertificateToDtoConverter;
import com.epam.esm.gift_system.service.converter.OrderToDtoConverter;
import com.epam.esm.gift_system.service.converter.TagToDtoConverter;
import com.epam.esm.gift_system.service.converter.UserToDtoConverter;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoConverterServiceImpl implements DtoConverterService {
    private final TagToDtoConverter tagToDtoConverter;
    private final UserToDtoConverter userToDtoConverter;
    private final OrderToDtoConverter orderToDtoConverter;
    private final GiftCertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public DtoConverterServiceImpl(TagToDtoConverter tagToDtoConverter, UserToDtoConverter userToDtoConverter
            , OrderToDtoConverter orderToDtoConverter, GiftCertificateToDtoConverter certificateToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
        this.userToDtoConverter = userToDtoConverter;
        this.orderToDtoConverter = orderToDtoConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    @Override
    public TagDto convertEntityIntoDto(Tag tag) {
        return tagToDtoConverter.convert(tag);
    }

    @Override
    public UserDto convertEntityIntoDto(User user) {
        return userToDtoConverter.convert(user);
    }

    @Override
    public ResponseOrderDto convertEntityIntoDto(Order order) {
        return orderToDtoConverter.convert(order);
    }

    @Override
    public GiftCertificateDto convertEntityIntoDto(GiftCertificate certificate) {
        return certificateToDtoConverter.convert(certificate);
    }
}