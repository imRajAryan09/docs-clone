package com.google.docs.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class RedisConstant {

    private RedisConstant() {
    }

    public static final Map<String, Integer> CACHE_MAP = ImmutableMap.<String, Integer>builder()
            .put("docs_delta", 86400000)  // 1 day
            .build();
}
