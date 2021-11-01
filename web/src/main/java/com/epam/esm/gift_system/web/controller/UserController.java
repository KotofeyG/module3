package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.OrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/name")                                            //todo
    public UserDto findByName(@RequestParam(value = "name") String name) {
        return userService.findByName(name);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public OrderDto findUserOrderList(@PathVariable Long userId, @PathVariable Long orderId) {
        return userService.findUserOrderById(userId, orderId);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> findUserOrderList(@PathVariable Long id) {
        return userService.findUserOrderList(id);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}