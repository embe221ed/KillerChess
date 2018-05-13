package com.killerchess.core.exceptions;

public class IncorrectRequestParameterFormatException extends RestApiException {

    private String parameter;

    public IncorrectRequestParameterFormatException(String parameter) {
        super(ApiExceptionEnum.INCORRECT_REQUEST_PARAMETER_FORMAT);
        this.parameter = parameter;
    }

    @Override
    public String getDefaultMessage() {
        return super.getDefaultMessage() + parameter;
    }
}
