package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
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
        List<Order> orderList = CollectionUtils.isEmpty(source.getOrderDtoList())
                ? Collections.EMPTY_LIST
                : source.getOrderDtoList().stream().map(toOrderConverter::convert).toList();
        return User.builder()
                .id(source.getId())
                .name(source.getName())
                .orderList(orderList)
                .build();
    }
}