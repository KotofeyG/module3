package com.epam.esm.gift_system.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateAttributeDto {
    private List<String> tagNameList;
    private String searchPart;
    private String orderSort;
    private List<String> sortingFieldList;
}