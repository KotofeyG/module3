package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.EntityConverterService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import com.epam.esm.gift_system.service.validator.EntityValidator.ValidationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserDao userDao;
    @Mock
    private EntityValidator validator;
    @Mock
    private DtoConverterService dtoConverter;
    @Mock
    private EntityConverterService entityConverter;

    private User user;
    private UserDto userDto;
    private CustomPageable pageable;
    private CustomPage<UserDto> userPage;
    private CustomPage<ResponseOrderDto> orderPage;
    private Order order;
    private ResponseOrderDto orderDto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("UserName").build();
        userDto = UserDto.builder().id(1L).name("UserName").build();
        pageable = new CustomPageable();
        pageable.setPage(10);
        pageable.setSize(1);
        userPage = new CustomPage<>(List.of(userDto, userDto), pageable, 30L);
        order =  Order.builder()
                .id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("100"))
                .user(user)
                .certificateList(List.of(GiftCertificate.builder().build()))
                .build();
        orderDto = ResponseOrderDto.builder()
                .id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("100"))
                .userDto(userDto)
                .certificateList(List.of(GiftCertificateDto.builder().build()))
                .build();
        orderPage = new CustomPage<>(List.of(orderDto, orderDto), pageable, 30L);
    }

    @Test
    void createWithValidParam() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(true).when(userDao).isUserNameFree(anyString());
        doReturn(user).when(entityConverter).convertDtoIntoEntity(any(UserDto.class));
        doReturn(userDto).when(dtoConverter).convertEntityIntoDto(any(User.class));
        doReturn(user).when(userDao).create(any(User.class));
        UserDto actual = service.create(userDto);
        assertEquals(userDto, actual);
    }

    @Test
    void createThrowsExceptionWithInvalidUserName() {
        doReturn(false).when(validator).isNameValid(anyString(), any(ValidationType.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(userDto));
        assertEquals(40040, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithExistentUserName() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(false).when(userDao).isUserNameFree(anyString());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(userDto));
        assertEquals(40911, thrown.getErrorCode());
    }

    @Test
    void update() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.update(1L, userDto));
        assertEquals("update method isn't implemented in UserServiceImpl class", thrown.getMessage());
    }

    @Test
    void findByIdWithExistentEntity() {
        doReturn(userDto).when(dtoConverter).convertEntityIntoDto(any(User.class));
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        UserDto actual = service.findById(1L);
        assertEquals(userDto, actual);
    }

    @Test
    void findByIdThrowsExceptionWithNonExistentEntity() {
        doReturn(Optional.empty()).when(userDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findUserByIdWithExistentEntity() {
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        User actual = service.findUserById(1L);
        assertEquals(user, actual);
    }

    @Test
    void findUserByIdWithNonExistentEntity() {
        doReturn(Optional.empty()).when(userDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findUserById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findByNameWithValidUserName() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(Optional.of(user)).when(userDao).findByName(anyString());
        doReturn(userDto).when(dtoConverter).convertEntityIntoDto(any(User.class));
        UserDto actual = service.findByName("UserName");
        assertEquals(userDto, actual);
    }

    @Test
    void findByNameWithInvalidUserName() {
        doReturn(false).when(validator).isNameValid(anyString(), any(ValidationType.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findByName("UserName"));
        assertEquals(40040, thrown.getErrorCode());
    }

    @Test
    void findByNameWithNonExistentEntity() {
        doReturn(true).when(validator).isNameValid(anyString(), any(ValidationType.class));
        doReturn(Optional.empty()).when(userDao).findByName(anyString());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findByName("UserName"));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findAllWithValidParam() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(30L).when(userDao).findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(user, user)).when(userDao).findAll(anyInt(), anyInt());
        doReturn(userDto).when(dtoConverter).convertEntityIntoDto(any(User.class));
        CustomPage<UserDto> actual = service.findAll(pageable);
        assertEquals(userPage, actual);
    }

    @Test
    void findAllWithInvalidPageable() {
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findAll(pageable));
        assertEquals(40050, thrown.getErrorCode());
    }

    @Test
    void findAllWithNonExistentPage() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(30L).when(userDao).findEntityNumber();
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findAll(pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void findUserOrderListWithValidParam() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doReturn(30L).when(userDao).findUserOrdersNumber(any(User.class));
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(order, order)).when(userDao).findUserOrders(any(User.class), anyInt(), anyInt());
        doReturn(orderDto).when(dtoConverter).convertEntityIntoDto(any(Order.class));
        CustomPage<ResponseOrderDto> actual = service.findUserOrderList(1L, pageable);
        assertEquals(orderPage, actual);
    }

    @Test
    void findUserOrderListThrowsExceptionWithInvalidPageable() {
        doReturn(false).when(validator).isPageDataValid(any(CustomPageable.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findUserOrderList(1L, pageable));
        assertEquals(40050, thrown.getErrorCode());
    }

    @Test
    void findUserOrderListThrowsExceptionWithNonExistentUser() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(Optional.empty()).when(userDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findUserOrderList(1L, pageable));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findUserOrderListThrowsExceptionWithNonExistentPage() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doReturn(30L).when(userDao).findUserOrdersNumber(any(User.class));
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findUserOrderList(1L, pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void delete() {
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doReturn(false).when(userDao).isUserHasOrders(any(User.class));
        doNothing().when(userDao).delete(any(User.class));
        service.delete(1L);
    }

    @Test
    void deleteThrowsExceptionWithNonExistentUser() {
        doReturn(Optional.empty()).when(userDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void deleteThrowsExceptionWithUserWhoUsedInOrders() {
        doReturn(Optional.of(user)).when(userDao).findById(anyLong());
        doReturn(true).when(userDao).isUserHasOrders(any(User.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.delete(1L));
        assertEquals(40910, thrown.getErrorCode());
    }
}