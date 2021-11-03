package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {
    private final GiftCertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public UserToDtoConverter(GiftCertificateToDtoConverter certificateToDtoConverter) {
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .name(source.getName())
                .orderDtoList(buildOrderDtoList(source.getOrderList()))
                .build();
    }

    private List<ResponseOrderDto> buildOrderDtoList(List<Order> orderList) {
        return Objects.isNull(orderList)
                ? Collections.EMPTY_LIST
                : orderList.stream()
                .map(order -> ResponseOrderDto.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .cost(order.getCost())
                        .certificateList(buildGiftCertificateDtoList(order.getCertificateList()))
                        .build())
                .toList();
    }

    private List<GiftCertificateDto> buildGiftCertificateDtoList(List<GiftCertificate> certificateList) {
        return certificateList.stream().map(certificateToDtoConverter::convert).toList();
    }
}