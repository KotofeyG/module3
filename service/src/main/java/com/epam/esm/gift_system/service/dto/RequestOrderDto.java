package com.epam.esm.gift_system.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderDto {
    private Long userId;
    private List<Long> certificateIdList;
}