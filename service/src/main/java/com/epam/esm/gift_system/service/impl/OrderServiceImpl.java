package com.epam.esm.gift_system.service.impl;

import com.epam.esm.gift_system.repository.dao.GiftCertificateDao;
import com.epam.esm.gift_system.repository.dao.OrderDao;
import com.epam.esm.gift_system.repository.dao.UserDao;
import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.service.ConverterService;
import com.epam.esm.gift_system.service.OrderService;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.exception.GiftSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.gift_system.service.exception.ErrorCode.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao certificateDao;
    private final ConverterService converter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao certificateDao, ConverterService converter) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.converter = converter;
    }

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {                         //todo. Break up
        if (Objects.isNull(orderDto)) {
            throw new GiftSystemException(NULLABLE_OBJECT);
        }
        if (Objects.isNull(orderDto.getUserId()) || userDao.findById(orderDto.getUserId()).isEmpty()) {
            throw new GiftSystemException(NON_EXISTENT_ENTITY);
        }
        List<GiftCertificateDto> certificateDtoList = Optional.ofNullable(orderDto.getCertificateList())
                .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
        List<GiftCertificate> certificateList = certificateDtoList.stream()
                .map(certificateDto -> certificateDao.findById(Optional.ofNullable(certificateDto.getId())
                        .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY)))
                    .orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY)))
                .toList();
        orderDto.setCertificateList(Collections.EMPTY_LIST);
        Order order = converter.convertDtoIntoEntity(orderDto);
        order.setCertificateList(certificateList);
        return converter.convertEntityIntoDto(orderDao.create(order));
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        throw new UnsupportedOperationException("update method isn't implemented in OrderServiceImpl class");
    }

    @Override
    public OrderDto findById(Long id) {
        return converter.convertEntityIntoDto(findOrderById(id));
    }

    private Order findOrderById(Long id) {
        return orderDao.findById(id).orElseThrow(() -> new GiftSystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public List<OrderDto> findAll() {
        return orderDao.findAll().stream().map(converter::convertEntityIntoDto).toList();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("delete method isn't implemented in OrderServiceImpl class");
    }
}