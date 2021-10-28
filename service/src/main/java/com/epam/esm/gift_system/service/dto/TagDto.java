package com.epam.esm.gift_system.service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto {
    private Long id;
    private String name;
}