package com.killerchess.core.services;

import com.killerchess.core.dto.RegisterDTO;
import com.killerchess.core.exceptions.IncorrectRequestParameterFormatException;
import com.killerchess.core.util.FieldNames;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    //TODO Usually, we will contact via service with repository. It's example.
    public RegisterDTO getResult(RegisterDTO registerDTO) throws Exception {
        if (registerDTO.getPassword() != null && registerDTO.getPassword().equals("OKON")) {
            throw new IncorrectRequestParameterFormatException(FieldNames.PASSWORD.getName());
        }
        return registerDTO;
    }
}
