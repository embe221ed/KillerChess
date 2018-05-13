package com.killerchess.core.response.api;

import com.killerchess.core.dto.RegisterDTO;
import com.killerchess.core.exceptions.ApiExceptionEnum;
import com.killerchess.core.util.FieldNames;

import java.util.Optional;

public class RegisterResponseEntity extends ApiResponseEntity {

    private Optional<RegisterDTO> register;

    public RegisterResponseEntity(Optional<RegisterDTO> register, ApiExceptionEnum apiExceptionEnum) {
        super(apiExceptionEnum.getDefaultMessage(),
                apiExceptionEnum.getHttpDefaultCode(),
                apiExceptionEnum.getHttpDefaultCode(),
                apiExceptionEnum.isSuccess());
        this.register = register;
    }

    @Override
    public ResponseMap toResponseMap() {
        ResponseMap responseMap = new ResponseMap();
        responseMap.put(new ResponseField(FieldNames.SUCCESS.getName(), 0),
                ApiExceptionEnum.SUCCESS.isSuccess());
        responseMap.put(new ResponseField(FieldNames.ERROR_CODE.getName(), 1),
                ApiExceptionEnum.SUCCESS.getStatusCode());
        register.ifPresent(registerDTO -> responseMap.put(new ResponseField(FieldNames.REGISTER.getName(), 2),
                registerDTO.mapToResponseMap()));
        return responseMap;
    }
}
