package com.google.docs.model.user;

import lombok.Getter;
import java.util.Map;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Getter
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

}
