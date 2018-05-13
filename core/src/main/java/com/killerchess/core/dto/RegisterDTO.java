package com.killerchess.core.dto;


import com.killerchess.core.response.api.ApiResponseData;
import com.killerchess.core.response.api.ResponseField;
import com.killerchess.core.response.api.ResponseMap;
import com.killerchess.core.util.FieldNames;

public class RegisterDTO implements ApiResponseData {

    //TODO in this place we must add lombok
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public ResponseMap mapToResponseMap() {
        ResponseMap responseMap = new ResponseMap();
        responseMap.put(new ResponseField(FieldNames.USERNAME.getName(), 0), username);
        responseMap.put(new ResponseField(FieldNames.PASSWORD.getName(), 1), password);
        return responseMap;
    }

}
