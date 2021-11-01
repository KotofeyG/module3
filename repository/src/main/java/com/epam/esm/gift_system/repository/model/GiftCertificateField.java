package com.epam.esm.gift_system.repository.model;

import java.util.Arrays;
import java.util.List;

public enum GiftCertificateField {
    ID("id"), NAME("name"), DESCRIPTION("description"), PRICE("price"), DURATION("duration")
    , CREATE_DATE("createDate"), LAST_UPDATE_DATE("lastUpdateDate");

    private final String name;

    GiftCertificateField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getNameList() {
        return Arrays.stream(GiftCertificateField.values()).map(GiftCertificateField::getName).toList();
    }
}