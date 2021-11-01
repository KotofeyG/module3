package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.model.*;
import com.epam.esm.gift_system.service.ConverterService;
import com.epam.esm.gift_system.service.converter.*;
import com.epam.esm.gift_system.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterServiceImpl implements ConverterService {
    private final DtoToTagConverter toTagConverter;
    private final TagToDtoConverter tagToDtoConverter;
    private final DtoToUserConverter toUserConverter;
    private final UserToDtoConverter userToDtoConverter;
    private final DtoToOrderConverter toOrderConverter;
    private final OrderToDtoConverter orderToDtoConverter;
    private final DtoToGiftCertificateConverter toCertificateConverter;
    private final GiftCertificateToDtoConverter certificateToDtoConverter;
    private final DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter;

    @Autowired
    public ConverterServiceImpl(DtoToTagConverter toTagConverter, TagToDtoConverter tagToDtoConverter
            , DtoToUserConverter toUserConverter, UserToDtoConverter userToDtoConverter
            , DtoToOrderConverter toOrderConverter, OrderToDtoConverter orderToDtoConverter
            , DtoToGiftCertificateConverter toCertificateConverter
            , GiftCertificateToDtoConverter certificateToDtoConverter
            , DtoToGiftCertificateAttributeConverter toCertificateAttributeConverter) {
        this.toTagConverter = toTagConverter;
        this.tagToDtoConverter = tagToDtoConverter;
        this.toUserConverter = toUserConverter;
        this.userToDtoConverter = userToDtoConverter;
        this.toOrderConverter = toOrderConverter;
        this.orderToDtoConverter = orderToDtoConverter;
        this.toCertificateConverter = toCertificateConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
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
    public Order convertDtoIntoEntity(OrderDto orderDto) {
        return toOrderConverter.convert(orderDto);
    }

    @Override
    public GiftCertificate convertDtoIntoEntity(GiftCertificateDto certificateDto) {
        return toCertificateConverter.convert(certificateDto);
    }

    @Override
    public GiftCertificateAttribute convertDtoIntoEntity(GiftCertificateAttributeDto certificateAttributeDto) {
        return toCertificateAttributeConverter.convert(certificateAttributeDto);
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
    public OrderDto convertEntityIntoDto(Order order) {
        return orderToDtoConverter.convert(order);
    }

    @Override
    public GiftCertificateDto convertEntityIntoDto(GiftCertificate certificate) {
        return certificateToDtoConverter.convert(certificate);
    }
}