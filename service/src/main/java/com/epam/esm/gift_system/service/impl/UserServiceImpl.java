package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.ConverterService;
import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.INSERT;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EntityValidator validator;
    private final ConverterService converter;

    @Autowired
    public UserServiceImpl(UserDao userDao, EntityValidator validator, ConverterService converter) {
        this.userDao = userDao;
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {                    //todo
        if (validator.isNameValid(userDto.getName(), INSERT)) {
            if (userDao.isUserNameFree(userDto.getName())) {
                User user = converter.convertDtoIntoEntity(userDto);
                return converter.convertEntityIntoDto(userDao.create(user));
            }
            throw new GiftSystemException(DUPLICATE_NAME);
        }
        throw new GiftSystemException(USER_INVALID_NAME);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        throw new UnsupportedOperationException("update method isn't implemented in UserServiceImpl class");
    }

    @Override
    public UserDto findById(Long id) {
        return converter.convertEntityIntoDto(findUserById(id));
    }

    private User findUserById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public UserDto findByName(String name) {
        if (!validator.isNameValid(name, INSERT)) {
            throw new GiftSystemException(USER_INVALID_NAME);
        }
        return userDao.findByName(name)
                .map(converter::convertEntityIntoDto)
                .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public OrderDto findUserOrderById(Long userId, Long orderId) {
        return userDao.findUserOrderById(userId, orderId)
                .map(converter::convertEntityIntoDto)
                .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<OrderDto> findUserOrderList(Long id) {
        User user = findUserById(id);
        return user.getOrderList().stream().map(converter::convertEntityIntoDto).toList();
    }

    @Override
    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(converter::convertEntityIntoDto).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findUserById(id);
        if (userDao.isUserHasOrders(id)) {
            throw new GiftSystemException(USED_ENTITY);
        }
        userDao.delete(user);
    }
}