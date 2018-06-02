package com.killerchess.core.services;

import com.killerchess.view.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    //TODO Usually, we will contact via service with repository. It's example.
    public Boolean isValidUser(UserDTO userDTO) {
        String password = userDTO.getPassword();
        return !password.toLowerCase().equals(userDTO.getUsername().toLowerCase())
                && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])" +
                "(?=.*[A-Z])(?!.*\\s).{8,}$") && password.length() <= 25;
    }
}
