package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.OrderDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
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
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl service;
    @Mock
    private OrderDao orderDao;
    @Mock
    private GiftCertificateService certificateService;
    @Mock
    private UserService userService;
    @Mock
    private EntityValidator validator;
    @Mock
    private DtoConverterService dtoConverter;

    private User user;
    private UserDto userDto;
    private GiftCertificate certificate;
    private Order order;
    private ResponseOrderDto orderDto;
    private RequestOrderDto request;
    private CustomPageable pageable;
    private CustomPage<ResponseOrderDto> orderPage;

    @BeforeEach
    public void SetUp() {
        user = User.builder().id(1L).name("UserName").build();
        userDto = UserDto.builder().id(1L).name("UserName").build();
        pageable = new CustomPageable();
        pageable.setPage(10);
        pageable.setSize(1);
        certificate = GiftCertificate.builder().id(1L).name("NewCertificate").build();
        order = Order.builder().id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("100"))
                .user(user)
                .certificateList(List.of(GiftCertificate.builder().build()))
                .build();
        orderDto = ResponseOrderDto.builder().id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("100"))
                .userDto(userDto)
                .certificateList(List.of(GiftCertificateDto.builder().build()))
                .build();
        request = RequestOrderDto.builder().userId(1L).certificateIdList(List.of(1L, 1L)).build();
        orderPage = new CustomPage<>(List.of(orderDto, orderDto), pageable, 30L);
    }

    @Test
    void createWithValidParam() {
        doReturn(true).when(validator).isRequestOrderDataValid(any(RequestOrderDto.class));
        doReturn(user).when(userService).findUserById(anyLong());
        doReturn(certificate).when(certificateService).findCertificateById(anyLong());
        doReturn(orderDto).when(dtoConverter).convertEntityIntoDto(any(Order.class));
        doReturn(order).when(orderDao).create(any(Order.class));
        ResponseOrderDto actual = service.create(request);
        assertEquals(orderDto, actual);
    }

    @Test
    void createThrowsExceptionWithNullParam() {
        RequestOrderDto orderDto = null;
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(orderDto));
        assertEquals(40010, thrown.getErrorCode());
    }

    @Test
    void createThrowsExceptionWithInvalidRequestData() {
        doReturn(false).when(validator).isRequestOrderDataValid(any(RequestOrderDto.class));
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.create(request));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void update() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.update(1L, orderDto));
        assertEquals("update method isn't implemented in OrderServiceImpl class", thrown.getMessage());
    }

    @Test
    void findByIdWithExistentEntity() {
        doReturn(orderDto).when(dtoConverter).convertEntityIntoDto(any(Order.class));
        doReturn(Optional.of(order)).when(orderDao).findById(anyLong());
        ResponseOrderDto actual = service.findById(1L);
        assertEquals(orderDto, actual);
    }

    @Test
    void findByIdWithNonExistentEntity() {
        doReturn(Optional.empty()).when(orderDao).findById(anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findById(1L));
        assertEquals(40410, thrown.getErrorCode());
    }

    @Test
    void findAll() {
        doReturn(true).when(validator).isPageDataValid(any(CustomPageable.class));
        doReturn(30L).when(orderDao).findEntityNumber();
        doReturn(true).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        doReturn(List.of(order, order)).when(orderDao).findAll(anyInt(), anyInt());
        doReturn(orderDto).when(dtoConverter).convertEntityIntoDto(any(Order.class));
        CustomPage<ResponseOrderDto> actual = service.findAll(pageable);
        assertEquals(orderPage, actual);
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
        doReturn(30L).when(orderDao).findEntityNumber();
        doReturn(false).when(validator).isPageExists(any(CustomPageable.class), anyLong());
        GiftSystemException thrown = assertThrows(GiftSystemException.class, () -> service.findAll(pageable));
        assertEquals(40051, thrown.getErrorCode());
    }

    @Test
    void delete() {
        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, () -> service.delete(1L));
        assertEquals("delete method isn't implemented in OrderServiceImpl class", thrown.getMessage());
    }
}