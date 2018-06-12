package com.killerchess.core.services;

import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])(?=.*[A-Z])(?!.*\\s).{8,}$";
    private static final int PASSWORD_MAX_LENGTH = 25;

    public Boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX)
                && password.length() <= PASSWORD_MAX_LENGTH;
    }

    public Boolean isValidUser(String login, String password) {
        return login != null && !login.equals("") && !password.toLowerCase().equals(login.toLowerCase());
    }
}
