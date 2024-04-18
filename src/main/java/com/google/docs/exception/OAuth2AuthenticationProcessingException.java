package com.google.docs.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
