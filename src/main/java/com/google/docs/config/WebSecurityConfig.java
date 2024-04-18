package com.google.docs.config;

import com.google.docs.auth.AuthenticationEntryPointExceptionHandler;
import com.google.docs.auth.OAuth2AuthenticationFailureHandler;
import com.google.docs.auth.OAuth2AuthenticationSuccessHandler;
import com.google.docs.filter.TokenAuthenticationFilter;
import com.google.docs.repository.CookieOAuth2AuthorizationRequestRepository;
import com.google.docs.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${frontend.url}")
    private String frontendUrl;


    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public CookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new CookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(auth -> auth.authenticationEntryPoint(new AuthenticationEntryPointExceptionHandler()))
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/",
                            "/error",
                            "/favicon.ico",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll();
                    auth.requestMatchers("/auth/**", "/oauth2/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.authorizationEndpoint(authorizationEndpointConfig -> {
                        authorizationEndpointConfig.baseUri("/oauth2/authorize");
                        authorizationEndpointConfig.authorizationRequestRepository(cookieAuthorizationRequestRepository());
                    });
                    oauth2.redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/oauth2/callback/*"));
                    oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService));
                    oauth2.successHandler(oAuth2AuthenticationSuccessHandler);
                    oauth2.failureHandler(oAuth2AuthenticationFailureHandler);
                })
                .build();

    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }
}
