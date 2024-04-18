package com.google.docs.exception;

import com.google.docs.enums.ErrorType;
import com.google.docs.enums.ExceptionType;
import org.springframework.http.HttpStatus;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class BadRequestException extends BaseException {

    private static final Integer ERROR_CODE = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorCode();
    private static final ErrorType ERROR_TYPE = ExceptionType.BAD_REQUEST_EXCEPTION.getErrorType();
    private static final HttpStatus HTTP_STATUS = ExceptionType.BAD_REQUEST_EXCEPTION.getHttpStatus();

    public BadRequestException() {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS);
    }

    public BadRequestException(String message) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message, cause);
    }
}
