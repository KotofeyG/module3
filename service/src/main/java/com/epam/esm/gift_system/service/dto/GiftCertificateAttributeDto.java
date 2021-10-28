package com.epam.esm.gift_system.service.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateAttributeDto {
    private String tagName;
    private String searchPart;
    private String orderSort;
    private List<String> sortingFields;
}