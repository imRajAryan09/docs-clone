package com.google.docs.exception;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
