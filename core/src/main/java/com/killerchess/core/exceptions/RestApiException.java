package com.killerchess.core.exceptions;

import com.killerchess.core.response.api.ApiResponseEntity;
import com.killerchess.core.response.api.RegisterResponseEntity;
import com.killerchess.core.util.FieldNames;

import java.util.Optional;

public abstract class RestApiException extends Exception {

    protected ApiExceptionEnum apiExceptionEnum;

    public RestApiException(ApiExceptionEnum apiExceptionEnum) {
        this.apiExceptionEnum = apiExceptionEnum;
    }

    public boolean isRejected() {
        return true;
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

    //TODO here we will add other enums. In a future "if" can be replaced be "switch case".
    public ApiResponseEntity toResponseEntity(String restApiEnum) {
        if (restApiEnum != null && restApiEnum.equals(FieldNames.REGISTER.getName())) {
            return generateRegisterResponse();
        }
        return null;
    }

    protected ApiResponseEntity generateRegisterResponse() {
        return new RegisterResponseEntity(Optional.empty(), apiExceptionEnum);
    }
}
