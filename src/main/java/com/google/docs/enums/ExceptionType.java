package com.google.docs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Getter
@AllArgsConstructor
public enum ExceptionType {

    BASE_EXCEPTION(1, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST_EXCEPTION(2, ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_EXCEPTION(3, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_PARSING_EXCEPTION(4, ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST),
    CONFIGURATION_SERVICE_EXCEPTION(5, ErrorType.EXTERNAL_SERVER, HttpStatus.BAD_GATEWAY),
    OPERATION_NOT_ALLOWED_EXCEPTION(6, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer errorCode;
    private final ErrorType errorType;
    private final HttpStatus httpStatus;
}