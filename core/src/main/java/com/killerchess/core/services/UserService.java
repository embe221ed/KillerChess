package com.killerchess.core.services;

import com.killerchess.core.exceptions.AuthenticationFailedException;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.repositories.RankingRepository;
import com.killerchess.core.repositories.UserRepository;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;

    @Autowired
    public UserService(UserRepository userRepository, RankingRepository rankingRepository) {
        this.userRepository = userRepository;
        this.rankingRepository = rankingRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<RankingRegistry> getRanking() {
        return rankingRepository.findAll();
    }

    @Override
    public Boolean isValidUser(String userLogin) throws AuthenticationFailedException{

        if(!userRepository.existsById(userLogin)){
            throw new AuthenticationFailedException();
        }
        return true;
    }

    @Override
    public User save(User entity) {
        userRepository.save(entity);
        return entity;
    }

    @Override
    public User find(User entity) {
        return userRepository.findByLogin(entity.getLogin());
    }
}
