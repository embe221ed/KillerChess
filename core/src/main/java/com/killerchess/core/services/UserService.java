package com.killerchess.core.services;

import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.repositories.UserRepository;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RankingService rankingService;
    private final int DEFAULT_POINTS_VALUE = 0;

    @Autowired
    public UserService(UserRepository userRepository, RankingService rankingService) {
        this.userRepository = userRepository;
        this.rankingService = rankingService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User entity) {
        userRepository.save(entity);
        RankingRegistry rankingRegistry = new RankingRegistry();
        rankingRegistry.setUserLogin(entity.getLogin());
        rankingRegistry.setUser(entity);
        rankingRegistry.setPoints(DEFAULT_POINTS_VALUE);
        rankingService.save(rankingRegistry);
        return entity;
    }

    public User find(User entity) {
        return userRepository.findByLogin(entity.getLogin());
    }

    public boolean existsUser(User entity) {
        return userRepository.existsById(entity.getLogin());
    }
}
