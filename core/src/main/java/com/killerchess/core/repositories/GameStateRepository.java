package com.killerchess.core.repositories;

import org.springframework.data.repository.Repository;

public interface GameStateRepository<GameState, GameStateIdentity> extends Repository<GameState, GameStateIdentity> {

    GameState save(GameState gameState);
    GameState findOne(GameStateIdentity gameStateIdentity);
}
