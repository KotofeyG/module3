package com.epam.esm.gift_system.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseOrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal cost;
    private UserDto userDto;
    private List<GiftCertificateDto> certificateList;
}