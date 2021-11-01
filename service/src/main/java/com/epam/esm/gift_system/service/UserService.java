package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;

import java.util.List;

public interface UserService extends BaseService<UserDto> {
    UserDto findByName(String name);

    OrderDto findUserOrderById(Long userId, Long orderId);

    List<OrderDto> findUserOrderList(Long id);
}