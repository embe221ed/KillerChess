package com.killerchess.core.exceptions;

public abstract class RestApiException extends Exception {

    private ApiExceptionEnum apiExceptionEnum;

    RestApiException(ApiExceptionEnum apiExceptionEnum) {
        this.apiExceptionEnum = apiExceptionEnum;
    }

    public String getName() {
        return apiExceptionEnum.getName();
    }

    public String getDefaultMessage() {
        return apiExceptionEnum.getDefaultMessage();
    }

    public Integer getHttpStatusCode() {
        return apiExceptionEnum.getHttpDefaultCode();
    }

    public Integer getStatusCode() {
        return apiExceptionEnum.getStatusCode();
    }

}
