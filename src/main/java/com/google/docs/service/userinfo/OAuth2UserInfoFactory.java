package com.google.docs.service.userinfo;

import com.google.docs.enums.AuthProvider;
import com.google.docs.exception.OAuth2AuthenticationProcessingException;
import com.google.docs.model.user.GoogleOAuth2UserInfo;
import com.google.docs.model.user.OAuth2UserInfo;

import java.util.Map;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
