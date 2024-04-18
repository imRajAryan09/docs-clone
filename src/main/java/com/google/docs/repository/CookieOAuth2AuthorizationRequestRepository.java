package com.google.docs.repository;

import com.google.docs.constant.AppConstant;
import com.google.docs.utils.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Component
public class CookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, AppConstant.Cookie.OAUTH2_AUTHORIZATION_REQUEST)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, AppConstant.Cookie.OAUTH2_AUTHORIZATION_REQUEST);
            CookieUtils.deleteCookie(request, response, AppConstant.Cookie.REDIRECT_URI_PARAM);
            return;
        }

        CookieUtils.addCookie(response, AppConstant.Cookie.OAUTH2_AUTHORIZATION_REQUEST, CookieUtils.serialize(authorizationRequest), AppConstant.Cookie.EXPIRY_SECONDS);
        String redirectUriAfterLogin = request.getParameter(AppConstant.Cookie.REDIRECT_URI_PARAM);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, AppConstant.Cookie.REDIRECT_URI_PARAM, redirectUriAfterLogin, AppConstant.Cookie.EXPIRY_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, AppConstant.Cookie.OAUTH2_AUTHORIZATION_REQUEST);
        CookieUtils.deleteCookie(request, response, AppConstant.Cookie.REDIRECT_URI_PARAM);
    }
}