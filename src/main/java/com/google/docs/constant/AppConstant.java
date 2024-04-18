package com.google.docs.constant;

import java.util.List;

/**
 * @author by Raj Aryan,
 * created on 16/04/2024
 */
public class AppConstant {

    private AppConstant() {
    }

    public static class Metric {
        public static final String EXECUTION_START_TIME = "executionStartTime";
        public static final String EXECUTION_TOTAL_TIME = "executionTotalTime";
        public static final String REQUEST_URI = "requestUri";
        public static final String HTTP_METHOD = "httpMethod";
        public static final String HTTP_STATUS_CODE = "httpStatusCode";

        private Metric() {
        }
    }

    public static class Authentication {
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final List<String> WHITE_LISTED_URI = List.of(
                "/",
                "/error",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js");

        private Authentication() {
        }
    }

    public static class RequestHeader {
        public static final String REQUEST_ID = "X-Request-ID";
        public static final String SERVER_ACCESS_TOKEN = "x-server-token";

        private RequestHeader() {
        }
    }

    public static class Cookie {
        public static final String OAUTH2_AUTHORIZATION_REQUEST = "oauth2_auth_request";
        public static final String REDIRECT_URI_PARAM = "redirect_uri";
        public static final int EXPIRY_SECONDS = 180;

        private Cookie() {
        }
    }
}
