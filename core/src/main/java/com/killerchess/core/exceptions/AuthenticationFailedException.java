package com.killerchess.core.exceptions;

public class AuthenticationFailedException extends RestApiException {
    public AuthenticationFailedException() {
        super(ApiExceptionEnum.AUTHENTICATION_FAILED);
    }
}
