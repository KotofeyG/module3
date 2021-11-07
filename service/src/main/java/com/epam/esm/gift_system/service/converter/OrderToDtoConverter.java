package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToDtoConverter implements Converter<Order, ResponseOrderDto> {
    private final GiftCertificateToDtoConverter certificateToDtoConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Autowired
    public OrderToDtoConverter(GiftCertificateToDtoConverter certificateToDtoConverter, UserToDtoConverter userToDtoConverter) {
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.userToDtoConverter = userToDtoConverter;
    }

    @Override
    public ResponseOrderDto convert(Order source) {
        return ResponseOrderDto.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .userDto(userToDtoConverter.convert(source.getUser()))
                .certificateList(source.getCertificateList().stream().map(certificateToDtoConverter::convert).toList())
                .build();
    }
}