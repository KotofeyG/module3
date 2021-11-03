package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.OrderDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.DtoConverterService;
import com.epam.esm.gift_system.service.GiftCertificateService;
import com.epam.esm.gift_system.service.OrderService;
import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import com.epam.esm.gift_system.service.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final GiftCertificateService certificateService;
    private final UserService userService;
    private final EntityValidator validator;
    private final DtoConverterService dtoConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, GiftCertificateService certificateService, UserService userService
            , EntityValidator validator, DtoConverterService dtoConverter) {
        this.orderDao = orderDao;
        this.certificateService = certificateService;
        this.userService = userService;
        this.validator = validator;
        this.dtoConverter = dtoConverter;
    }

    @Override
    @Transactional
    public ResponseOrderDto create(RequestOrderDto orderDto) {
        checkRequestOrderDto(orderDto);
        User user = userService.findUserById(orderDto.getUserId());
        List<GiftCertificate> certificateList = orderDto.getCertificateIdList().stream()
                .map(certificateService::findCertificateById).toList();
        Order order = Order.builder().user(user).certificateList(certificateList).build();
        return dtoConverter.convertEntityIntoDto(orderDao.create(order));
    }

    private void checkRequestOrderDto(RequestOrderDto orderDto) {
        if (Objects.isNull(orderDto)) {
            throw new GiftSystemException(NULLABLE_OBJECT);
        }
        if (!validator.isRequestOrderDataValid(orderDto)) {
            throw new GiftSystemException(NON_EXISTENT_ENTITY);
        }
    }

    @Override
    public ResponseOrderDto create(ResponseOrderDto orderDto) {
        throw new UnsupportedOperationException("create method with OrderDto parameter isn't implemented in OrderServiceImpl class");
    }

    @Override
    public ResponseOrderDto update(Long id, ResponseOrderDto orderDto) {
        throw new UnsupportedOperationException("update method isn't implemented in OrderServiceImpl class");
    }

    @Override
    public ResponseOrderDto findById(Long id) {
        return dtoConverter.convertEntityIntoDto(findOrderById(id));
    }

    private Order findOrderById(Long id) {
        return orderDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<ResponseOrderDto> findAll() {
        return orderDao.findAll().stream().map(dtoConverter::convertEntityIntoDto).toList();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("delete method isn't implemented in OrderServiceImpl class");
    }
}