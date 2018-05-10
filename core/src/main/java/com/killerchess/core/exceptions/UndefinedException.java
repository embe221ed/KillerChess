package com.killerchess.core.exceptions;

public class UndefinedException extends RestApiException {

    private Throwable throwable;

    public UndefinedException(Throwable throwable) {
        super(ApiExceptionEnum.UNDEFINED);
        this.throwable = throwable;
        this.setStackTrace(throwable.getStackTrace());
    }

    @Override
    public String getMessage() {
        return throwable.getMessage();
    }
}
