package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {
    private final OrderToDtoConverter orderToDtoConverter;

    @Autowired
    public UserToDtoConverter(OrderToDtoConverter orderToDtoConverter) {
        this.orderToDtoConverter = orderToDtoConverter;
    }

    @Override
    public UserDto convert(User source) {
        List<OrderDto> orderDtoList = CollectionUtils.isEmpty(source.getOrderList())
                ? Collections.EMPTY_LIST
                : source.getOrderList().stream().map(orderToDtoConverter::convert).toList();
        return UserDto.builder()
                .id(source.getId())
                .name(source.getName())
                .orderDtoList(orderDtoList)
                .build();
    }
}