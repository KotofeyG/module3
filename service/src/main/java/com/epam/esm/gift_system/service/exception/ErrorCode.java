package com.epam.esm.gift_system.service.exception;

public class ErrorCode {
    public static final int BAD_REQUEST = 40000;
    public static final int CERTIFICATE_INVALID_NAME = 40001;
    public static final int CERTIFICATE_INVALID_DESCRIPTION = 40002;
    public static final int CERTIFICATE_INVALID_PRICE = 40003;
    public static final int CERTIFICATE_INVALID_DURATION = 40004;
    public static final int UNREADABLE_MESSAGE = 40005;
    public static final int INVALID_NAME = 40006;
    public static final int NULLABLE_OBJECT = 40007;
    public static final int INVALID_ATTRIBUTE_LIST = 40008;

    public static final int NON_EXISTENT_ENTITY = 40401;

    public static final int DUPLICATE_NAME = 40901;
    public static final int USED_ENTITY = 40902;

    private ErrorCode() {
    }
}