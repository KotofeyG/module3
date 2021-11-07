package com.epam.esm.gift_system.service.converter;

import com.epam.esm.gift_system.repository.model.GiftCertificate;
import com.epam.esm.gift_system.repository.model.Order;
import com.epam.esm.gift_system.repository.model.Tag;
import com.epam.esm.gift_system.repository.model.User;
import com.epam.esm.gift_system.service.dto.GiftCertificateDto;
import com.epam.esm.gift_system.service.dto.ResponseOrderDto;
import com.epam.esm.gift_system.service.dto.TagDto;
import com.epam.esm.gift_system.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderToDtoConverterTest {
    @InjectMocks
    private OrderToDtoConverter orderToDtoConverter;
    @Mock
    private GiftCertificateToDtoConverter certificateToDtoConverter;
    @Mock
    private UserToDtoConverter userToDtoConverter;

    private User user;
    private UserDto userDto;
    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;
    private Order order;
    private ResponseOrderDto orderDto;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(1L).name("User").build();
        userDto = UserDto.builder().id(1L).name("User").build();
        certificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("FirstCertificateName")
                .description("FirstCertificateDescription")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagDtoList(List.of(TagDto.builder().id(1L).name("FirstTag").build(), TagDto.builder().id(2L).name("SecondTag").build()))
                .build();
        certificate = GiftCertificate.builder()
                .id(1L)
                .name("FirstCertificateName")
                .description("FirstCertificateDescription")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tagList(Set.of(Tag.builder().id(1L).name("FirstTag").build(), Tag.builder().id(2L).name("SecondTag").build()))
                .build();
        order = Order.builder()
                .id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("10"))
                .user(user)
                .certificateList(List.of(certificate))
                .build();
        orderDto = ResponseOrderDto.builder()
                .id(1L)
                .orderDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .cost(new BigDecimal("10"))
                .userDto(userDto)
                .certificateList(List.of(certificateDto))
                .build();
    }

    @Test
    void convert() {
        doReturn(userDto).when(userToDtoConverter).convert(Mockito.any(User.class));
        doReturn(certificateDto).when(certificateToDtoConverter).convert(Mockito.any(GiftCertificate.class));
        ResponseOrderDto actual = orderToDtoConverter.convert(order);
        assertEquals(orderDto, actual);
    }
}