package com.google.docs.interceptor;

import com.google.docs.constant.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${server.access.token}")
    private String envAccessToken;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        final String accessToken = request.getHeader(AppConstant.RequestHeader.SERVER_ACCESS_TOKEN);
        ThreadContext.put(AppConstant.RequestHeader.SERVER_ACCESS_TOKEN, accessToken);
        if (Boolean.FALSE.equals(isAccessTokenValid(accessToken))) {
            log.error("AUTH_INTERCEPTOR_ERROR :: Authentication failed for access_token: {}", accessToken);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }

    private Boolean isAccessTokenValid(String accessToken) {
        return envAccessToken.equals(accessToken);
    }
}