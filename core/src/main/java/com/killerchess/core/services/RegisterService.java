package com.killerchess.core.services;

import com.killerchess.core.user.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    //TODO Usually, we will contact via service with repository. It's example.
    public Boolean isValidUser(User user) {
        String password = user.getPassword();
        return !password.toLowerCase().equals(user.getLogin().toLowerCase())
                && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])" +
                "(?=.*[A-Z])(?!.*\\s).{8,}$") && password.length() <= 25;
    }
}
