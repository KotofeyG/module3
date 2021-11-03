package com.epam.esm.gift_system.repository.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GiftCertificateAttribute {
    private List<String> tagNameList;
    private String searchPart;
    private String orderSort;
    private List<String> sortingFieldList;
}