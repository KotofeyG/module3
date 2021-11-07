package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;

public interface UserService extends BaseService<UserDto> {
    User findUserById(Long id);

    UserDto findByName(String name);

    CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable);
}