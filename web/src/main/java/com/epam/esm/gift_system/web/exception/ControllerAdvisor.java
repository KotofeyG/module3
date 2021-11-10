package com.epam.esm.gift_system.web.exception;

import com.epam.esm.gift_system.service.exception.GiftSystemException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static com.epam.esm.gift_system.service.exception.ErrorCode.BAD_REQUEST;
import static com.epam.esm.gift_system.service.exception.ErrorCode.DATA_BASE_ERROR;
import static com.epam.esm.gift_system.service.exception.ErrorCode.UNREADABLE_MESSAGE;

@RestControllerAdvice
public class ControllerAdvisor {
    private static final int ONE_HUNDRED = 100;
    private static final Object[] EMPTY_ARGS = new Object[0];
    private static final String INITIAL_ERROR_MSG = "error_msg.";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    private final ResourceBundleMessageSource messages;

    @Autowired
    public ControllerAdvisor(ResourceBundleMessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(GiftSystemException.class)
    public ResponseEntity<Object> handleGiftSystemException(GiftSystemException e, Locale locale) {
        final int errorCode = e.getErrorCode();
        return new ResponseEntity<>(createResponse(errorCode, locale), getHttpStatusByCode(errorCode));
    }

    @ExceptionHandler({NumberFormatException.class, BindException.class})
    public ResponseEntity<Object> handleNumberFormatException(Locale locale) {
        return new ResponseEntity<>(createResponse(BAD_REQUEST, locale), getHttpStatusByCode(BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Locale locale) {
        return new ResponseEntity<>(createResponse(UNREADABLE_MESSAGE, locale), getHttpStatusByCode(UNREADABLE_MESSAGE));
    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<Object> handleJDBCConnectionException(Locale locale) {
        return new ResponseEntity<>(createResponse(DATA_BASE_ERROR, locale), getHttpStatusByCode(DATA_BASE_ERROR));
    }

    private Map<String, Object> createResponse(int errorCode, Locale locale) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(getMessageByCode(errorCode), EMPTY_ARGS, locale));
        response.put(ERROR_CODE, errorCode);
        return response;
    }

    private String getMessageByCode(int errorCode) {
        return INITIAL_ERROR_MSG + errorCode;
    }

    private HttpStatus getHttpStatusByCode(int errorCode) {
        int statusCode = errorCode / ONE_HUNDRED;
        return HttpStatus.valueOf(statusCode);
    }
}