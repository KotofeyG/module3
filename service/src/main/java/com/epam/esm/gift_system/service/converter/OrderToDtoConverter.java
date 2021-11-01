package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToDtoConverter implements Converter<Order, OrderDto> {
    private final GiftCertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public OrderToDtoConverter(GiftCertificateToDtoConverter certificateToDtoConverter) {
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    @Override
    public OrderDto convert(Order source) {
        return OrderDto.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .userId(source.getUserId())
                .certificateList(source.getCertificateList().stream().map(certificateToDtoConverter::convert).toList())
                .build();
    }
}