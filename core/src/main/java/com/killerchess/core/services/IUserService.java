package com.killerchess.core.services;

import com.killerchess.core.exceptions.AuthenticationFailedException;
import com.killerchess.core.user.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    Boolean isValidUser(String userLogin) throws AuthenticationFailedException;
}
