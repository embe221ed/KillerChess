package com.killerchess.core.services;

import com.killerchess.core.game.Game;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.repositories.GameRepository;
import com.killerchess.core.repositories.RankingRepository;
import com.killerchess.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    @Autowired
    public GameService(RankingRepository rankingRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.rankingRepository = rankingRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void initNewGame(String hostName, String guestName){

        Game newGame = new Game();
        newGame.setHost(userRepository.findByLogin(hostName));
        newGame.setGuest(userRepository.findByLogin(guestName));
        gameRepository.save(newGame);
    }
}
