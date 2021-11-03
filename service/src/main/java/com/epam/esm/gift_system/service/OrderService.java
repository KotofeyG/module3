package com.epam.esm.gift_system.service;

import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;

public interface OrderService extends BaseService<ResponseOrderDto> {
    ResponseOrderDto create(RequestOrderDto orderDto);
}