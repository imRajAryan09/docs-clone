package com.google.docs.config;

import com.google.docs.interceptor.AuthInterceptor;
import com.google.docs.interceptor.MetricInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final MetricInterceptor metricInterceptor;

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(metricInterceptor)
                .order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/testing/**", "/ping", "/actuator/**");
    }
}