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
public class DtoToOrderConverter implements Converter<ResponseOrderDto, Order> {
    private final DtoToGiftCertificateConverter toCertificateConverter;

    @Autowired
    public DtoToOrderConverter(DtoToGiftCertificateConverter toCertificateConverter) {
        this.toCertificateConverter = toCertificateConverter;
    }

    @Override
    public Order convert(ResponseOrderDto source) {
        return Order.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .user(buildUser(source.getUserDto()))
                .certificateList(source.getCertificateList().stream().map(toCertificateConverter::convert).toList())
                .build();
    }

    private User buildUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .orderList(Collections.EMPTY_LIST)
                .build();
    }
}