package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;

public interface DtoConverterService {
    TagDto convertEntityIntoDto(Tag tag);

    UserDto convertEntityIntoDto(User user);

    ResponseOrderDto convertEntityIntoDto(Order order);

    GiftCertificateDto convertEntityIntoDto(GiftCertificate certificate);
}