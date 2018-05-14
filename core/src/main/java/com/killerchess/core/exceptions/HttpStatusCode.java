package com.killerchess.core.exceptions;

public enum HttpStatusCode {
    HTTP_OK(200),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500);

    private final Integer statusCode;

    HttpStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

