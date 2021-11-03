package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
public class DtoToUserConverter implements Converter<UserDto, User> {
    private final DtoToOrderConverter toOrderConverter;

    @Autowired
    public DtoToUserConverter(DtoToOrderConverter toOrderConverter) {
        this.toOrderConverter = toOrderConverter;
    }

    @Override
    public User convert(UserDto source) {
        return User.builder()
                .id(source.getId())
                .name(source.getName())
                .orderList(buildOrderList(source.getOrderDtoList()))
                .build();
    }

    private List<Order> buildOrderList(List<ResponseOrderDto> orderDtoList) {
        return CollectionUtils.isEmpty(orderDtoList)
                ? Collections.EMPTY_LIST
                : orderDtoList.stream().map(toOrderConverter::convert).toList();
    }
}