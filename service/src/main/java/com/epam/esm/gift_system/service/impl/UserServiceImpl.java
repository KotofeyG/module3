package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;
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
    public List<UserDto> findAllByNameList(List<String> nameList) {
        checkNameList(nameList);
        return Objects.nonNull(nameList)
                ? userDao.findAllByNameList(nameList).stream().map(dtoConverter::convertEntityIntoDto).toList()
                :findAll();
    }

    private void checkNameList(List<String> nameList) {
        if (Objects.nonNull(nameList) && !nameList.stream().allMatch(name -> validator.isNameValid(name, STRONG))) {
            throw new GiftSystemException(USER_INVALID_NAME);
        }
    }

    @Override
    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(dtoConverter::convertEntityIntoDto).toList();
    }

    @Override
    public List<ResponseOrderDto> findUserOrderList(Long id) {
        User user = findUserById(id);
        return user.getOrderList().stream().map(dtoConverter::convertEntityIntoDto).toList();
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