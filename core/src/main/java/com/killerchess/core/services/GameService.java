package com.killerchess.core.services;

import com.killerchess.core.game.Game;
import com.killerchess.core.game.GameState;
import com.killerchess.core.game.GameStateIdentity;
import com.killerchess.core.repositories.GameRepository;
import com.killerchess.core.repositories.GameStateRepository;
import com.killerchess.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameStateRepository gameStateRepository;
    @Autowired
    public GameService(UserRepository userRepository, GameRepository gameRepository, GameStateRepository gameStateRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameStateRepository = gameStateRepository;
    }

    public void initNewGame(String hostName, String guestName){

        Game newGame = new Game();
        newGame.setHost(userRepository.findByLogin(hostName));
        newGame.setGuest(userRepository.findByLogin(guestName));
        gameRepository.save(newGame);
    }

    public String getSpecificGameState(Integer gameId, Integer gameStateNumber)throws NullPointerException{

        GameStateIdentity gameStateIdentity = new GameStateIdentity();
        Game game = gameRepository.findByGameId(gameId);

        if(game == null)
            throw new NullPointerException();

        gameStateIdentity.setGame(game);
        gameStateIdentity.setGameStateNumber(gameStateNumber);

        GameState gameState = gameStateRepository.findByGameStateIdentity(gameStateIdentity);
        if(gameState == null)
            throw new NullPointerException();

        return gameState.getState();
    }

    public void saveSpecificGameState(Integer gameId, String gameStateStr) {

        GameState gameStateInstance = new GameState();
        gameStateInstance.setState(gameStateStr);

        Game game = gameRepository.findByGameId(gameId);

        if(game == null)
            throw new NullPointerException();

        GameStateIdentity gameStateIdentity = new GameStateIdentity();
        gameStateIdentity.setGame(game);
        gameStateInstance.setGameStateIdentity(gameStateIdentity);

        gameStateRepository.save(gameStateInstance);
    }

}
