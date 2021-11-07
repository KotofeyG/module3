package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtoToUserConverterTest {
    private DtoToUserConverter toUserConverter;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        toUserConverter = new DtoToUserConverter();
        user = User.builder().id(1L).name("User").build();
        userDto = UserDto.builder().id(1L).name("User").build();
    }

    @Test
    void convert() {
        User actual = toUserConverter.convert(userDto);
        assertEquals(user, actual);
    }
}