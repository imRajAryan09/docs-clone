package com.google.docs.enums;

import lombok.AllArgsConstructor;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@AllArgsConstructor
public enum AuthProvider {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    LINKEDIN("linkedin");

    private final String source;
    }
