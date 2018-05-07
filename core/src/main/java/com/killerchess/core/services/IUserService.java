package com.killerchess.core.services;

import com.killerchess.core.user.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
}
