package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.GiftCertificateAttribute;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.GiftCertificateAttributeDto;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;

public interface ConverterService {
    Tag convertDtoIntoEntity(TagDto tagDto);

    User convertDtoIntoEntity(UserDto userDto);

    Order convertDtoIntoEntity(OrderDto orderDto);

    GiftCertificate convertDtoIntoEntity(GiftCertificateDto certificateDto);

    GiftCertificateAttribute convertDtoIntoEntity(GiftCertificateAttributeDto certificateAttributeDto);

    TagDto convertEntityIntoDto(Tag tag);

    UserDto convertEntityIntoDto(User user);

    OrderDto convertEntityIntoDto(Order order);

    GiftCertificateDto convertEntityIntoDto(GiftCertificate certificate);
}