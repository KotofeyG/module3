package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToOrderConverter implements Converter<OrderDto, Order> {
    private final DtoToGiftCertificateConverter toCertificateConverter;

    @Autowired
    public DtoToOrderConverter(DtoToGiftCertificateConverter toCertificateConverter) {
        this.toCertificateConverter = toCertificateConverter;
    }

    @Override
    public Order convert(OrderDto source) {
        return Order.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .userId(source.getUserId())
                .certificateList(source.getCertificateList().stream().map(toCertificateConverter::convert).toList())
                .build();
    }
}