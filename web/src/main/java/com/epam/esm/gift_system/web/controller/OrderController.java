package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.OrderService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;
import com.epam.esm.gift_system.web.hateaos.HateaosBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public OrderController(OrderService orderService, HateaosBuilder hateaosBuilder) {
        this.orderService = orderService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrderDto create(@RequestBody RequestOrderDto orderDto) {
        ResponseOrderDto created = orderService.create(orderDto);
        hateaosBuilder.setLinks(created);
        return created;
    }

    @GetMapping("/{id}")
    public ResponseOrderDto findById(@PathVariable Long id) {
        ResponseOrderDto orderDto = orderService.findById(id);
        hateaosBuilder.setLinks(orderDto);
        return orderDto;
    }

    @GetMapping
    public CustomPage<ResponseOrderDto> findAll(CustomPageable pageable) {
        CustomPage<ResponseOrderDto> page = orderService.findAll(pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }
}