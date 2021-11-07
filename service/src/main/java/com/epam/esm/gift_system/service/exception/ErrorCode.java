package com.epam.esm.gift_system.service.exception;

public class ErrorCode {
    public static final int BAD_REQUEST = 40000;
    public static final int UNREADABLE_MESSAGE = 40001;

    public static final int NULLABLE_OBJECT = 40010;

    public static final int TAG_INVALID_NAME = 40020;

    public static final int CERTIFICATE_INVALID_NAME = 40030;
    public static final int CERTIFICATE_INVALID_DESCRIPTION = 40031;
    public static final int CERTIFICATE_INVALID_PRICE = 40032;
    public static final int CERTIFICATE_INVALID_DURATION = 40033;
    public static final int INVALID_ATTRIBUTE_LIST = 40034;

    public static final int USER_INVALID_NAME = 40040;

    public static final int INVALID_DATA_OF_PAGE = 40050;
    public static final int NON_EXISTENT_PAGE = 40051;

    public static final int NON_EXISTENT_ENTITY = 40410;

    public static final int USED_ENTITY = 40910;
    public static final int DUPLICATE_NAME = 40911;

    private ErrorCode() {
    }
}