package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}