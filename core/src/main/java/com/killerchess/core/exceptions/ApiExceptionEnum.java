package com.killerchess.core.exceptions;

import org.springframework.http.HttpStatus;

public enum ApiExceptionEnum {

    SUCCESS("SUCCESS", "", HttpStatus.OK, 0, true),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "Authentication failed",
            HttpStatus.FORBIDDEN, 1, false),
    INVALID_SECRET_KEY("INVALID_SECRET_KEY", "Secret key value is invalid",
            HttpStatus.FORBIDDEN, 2, false),
    INCORRECT_REQUEST_PARAMETER_FORMAT("INCORRECT_REQUEST_PARAMETER_FORMAT",
            "Incorrect request parameter format, field name: ",
            HttpStatus.BAD_REQUEST, 3, false),
    UNDEFINED("UNDEFINED", "Request failed",
            HttpStatus.INTERNAL_SERVER_ERROR, 99, false);


    private String name;
    private String defaultMessage;
    private HttpStatus httpDefaultCode;
    private Integer statusCode;
    private boolean success;

    ApiExceptionEnum(String name, String defaultMessage, HttpStatus httpDefaultCode, Integer statusCode, Boolean success) {
        this.name = name;
        this.defaultMessage = defaultMessage;
        this.httpDefaultCode = httpDefaultCode;
        this.statusCode = statusCode;
        this.success = success;

    }

    public String getName() {
        return name;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public HttpStatus getHttpDefaultCode() {
        return httpDefaultCode;
    }
}
