package com.killerchess.core.exceptions;

public class InvalidSecretKeyException extends RestApiException {

    public InvalidSecretKeyException() {
        super(ApiExceptionEnum.INVALID_SECRET_KEY);
    }
}
