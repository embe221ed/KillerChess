package com.killerchess.core.repositories;

import org.springframework.data.repository.Repository;

public interface GameRepository <Game, Integer> extends Repository<Game, Integer> {

    Game save(Game game);
    Game findOne(Integer gameId);
    void delete(Game gameToDelete);
    boolean exists(String primaryKey);
}