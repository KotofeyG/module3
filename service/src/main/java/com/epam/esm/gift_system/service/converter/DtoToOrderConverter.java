package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToOrderConverter implements Converter<ResponseOrderDto, Order> {
    private final DtoToGiftCertificateConverter toCertificateConverter;
    private final DtoToUserConverter toUserConverter;

    @Autowired
    public DtoToOrderConverter(DtoToGiftCertificateConverter toCertificateConverter, DtoToUserConverter toUserConverter) {
        this.toCertificateConverter = toCertificateConverter;
        this.toUserConverter = toUserConverter;
    }

    @Override
    public Order convert(ResponseOrderDto source) {
        return Order.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .cost(source.getCost())
                .user(toUserConverter.convert(source.getUserDto()))
                .certificateList(source.getCertificateList().stream().map(toCertificateConverter::convert).toList())
                .build();
    }
}