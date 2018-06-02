package com.killerchess.core.services;

import com.killerchess.core.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    //TODO Usually, we will contact via service with repository. It's example.
    public Boolean isValidUser(UserDTO userDTO) {
        return userDTO.getPassword().length() >= 8 && !userDTO.getPassword().equals(userDTO.getUsername());
    }
}
