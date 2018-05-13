package com.killerchess.core.exceptions;

public enum ApiExceptionEnum {

    SUCCESS("SUCCESS", "", 200, 0, true),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "Authentication failed", 403, 1, false),
    INVALID_SECRET_KEY("INVALID_SECRET_KEY", "Secret key value is invalid", 403, 2, false),
    INCORRECT_REQUEST_PARAMETER_FORMAT("INCORRECT_REQUEST_PARAMETER_FORMAT",
            "Incorrect request parameter format, field name: ", 400, 3, false),
    UNDEFINED("UNDEFINED", "Request failed", 500, 99, false);


    private String name;
    private String defaultMessage;
    private Integer httpDefaultCode;
    private Integer statusCode;
    private boolean success;

    ApiExceptionEnum(String name, String defaultMessage, Integer httpDefaultCode, Integer statusCode, Boolean success) {
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

    public Integer getHttpDefaultCode() {
        return httpDefaultCode;
    }
}
