package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OrderToDtoConverter implements Converter<Order, ResponseOrderDto> {
    private final GiftCertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public OrderToDtoConverter(GiftCertificateToDtoConverter certificateToDtoConverter) {
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    @Override
    public ResponseOrderDto convert(Order source) {
        return ResponseOrderDto.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .userDto(buildUserDto(source.getUser()))
                .certificateList(source.getCertificateList().stream().map(certificateToDtoConverter::convert).toList())
                .build();
    }

    private UserDto buildUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .orderDtoList(Collections.EMPTY_LIST)
                .build();
    }
}