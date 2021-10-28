package com.epam.esm.gift_system.service.exception;

public class GiftSystemException extends RuntimeException {
    private final int errorCode;

    public GiftSystemException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}