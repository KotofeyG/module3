package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;

import java.util.List;

public interface UserService extends BaseService<UserDto> {
    User findUserById(Long id);

    List<UserDto> findAllByNameList(List<String> nameList);

    List<ResponseOrderDto> findUserOrderList(Long id);
}