package com.killerchess.core.response.api;

public abstract class ApiResponseEntity {

    protected String message;
    protected int errorCode;
    protected boolean success;
    protected int httpStatusCode;

    public ApiResponseEntity(String message, int errorCode, int httpStatusCode, boolean success) {
        this.message = message;
        this.errorCode = errorCode;
        this.success = success;
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public abstract ResponseMap toResponseMap();
}