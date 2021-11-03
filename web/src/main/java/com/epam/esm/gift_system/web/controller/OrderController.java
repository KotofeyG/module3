package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.OrderService;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.RequestOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrderDto create(@RequestBody RequestOrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @GetMapping("/{id}")
    public ResponseOrderDto findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping
    public List<ResponseOrderDto> findAll() {
        return orderService.findAll();
    }
}