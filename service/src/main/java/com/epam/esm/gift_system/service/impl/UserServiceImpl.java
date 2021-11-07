package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.gift_system.service.exception.ErrorCode.DUPLICATE_NAME;
import static com.epam.esm.gift_system.service.exception.ErrorCode.INVALID_DATA_OF_PAGE;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.NON_EXISTENT_PAGE;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USED_ENTITY;
import static com.epam.esm.gift_system.service.exception.ErrorCode.USER_INVALID_NAME;
import static com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType.STRONG;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final EntityValidator validator;
    private final DtoConverterService dtoConverter;
    private final EntityConverterService entityConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, EntityValidator validator, DtoConverterService dtoConverter
            , EntityConverterService entityConverter) {
        this.userDao = userDao;
        this.validator = validator;
        this.dtoConverter = dtoConverter;
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        if (validator.isNameValid(userDto.getName(), STRONG)) {
            if (userDao.isUserNameFree(userDto.getName())) {
                User user = entityConverter.convertDtoIntoEntity(userDto);
                return dtoConverter.convertEntityIntoDto(userDao.create(user));
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
        return dtoConverter.convertEntityIntoDto(findUserById(id));
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public UserDto findByName(String name) {
        if (!validator.isNameValid(name, STRONG)) {
            throw new GiftSystemException(USER_INVALID_NAME);
        }
        return userDao.findByName(name).map(dtoConverter::convertEntityIntoDto)
                .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        if (!validator.isPageDataValid(pageable)) {
            throw new GiftSystemException(INVALID_DATA_OF_PAGE);
        }
        long totalUserNumber = userDao.findEntityNumber();
        if (!validator.isPageExists(pageable, totalUserNumber)) {
            throw new GiftSystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<UserDto> userDtoList = userDao.findAll(offset, pageable.getSize())
                .stream().map(dtoConverter::convertEntityIntoDto).toList();
        return new CustomPage<>(userDtoList, pageable, totalUserNumber);
    }

    @Override
    public CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable) {
        if (!validator.isPageDataValid(pageable)) {
            throw new GiftSystemException(INVALID_DATA_OF_PAGE);
        }
        User user = findUserById(id);
        long totalOrderNumber = userDao.findUserOrdersNumber(user);
        if (!validator.isPageExists(pageable, totalOrderNumber)) {
            throw new GiftSystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<ResponseOrderDto> orderDtoList = userDao.findUserOrders(user, offset, pageable.getSize())
                .stream().map(dtoConverter::convertEntityIntoDto).toList();
        return new CustomPage<>(orderDtoList, pageable, totalOrderNumber);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findUserById(id);
        if (userDao.isUserHasOrders(user)) {
            throw new GiftSystemException(USED_ENTITY);
        }
        userDao.delete(user);
    }
}