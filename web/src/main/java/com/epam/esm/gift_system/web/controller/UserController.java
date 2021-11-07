package com.epam.esm.gift_system.web.controller;

import com.epam.esm.gift_system.service.UserService;
import com.epam.esm.gift_system.service.dto.CustomPage;
import com.epam.esm.gift_system.service.dto.CustomPageable;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import com.epam.esm.gift_system.web.hateaos.HateaosBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HateaosBuilder hateaosBuilder;

    @Autowired
    public UserController(UserService userService, HateaosBuilder hateaosBuilder) {
        this.userService = userService;
        this.hateaosBuilder = hateaosBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        UserDto created = userService.create(userDto);
        hateaosBuilder.setLinks(created);
        return created;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        hateaosBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping("/search/{name}")
    public UserDto findByName(@PathVariable String name) {
        UserDto userDto = userService.findByName(name);
        hateaosBuilder.setLinks(userDto);
        return userDto;
    }

    @GetMapping
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        CustomPage<UserDto> page = userService.findAll(pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }

    @GetMapping("/{id}/orders")
    public CustomPage<ResponseOrderDto> findUserOrderList(@PathVariable Long id, CustomPageable pageable) {
        CustomPage<ResponseOrderDto> page = userService.findUserOrderList(id, pageable);
        page.getContent().forEach(hateaosBuilder::setLinks);
        return page;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}