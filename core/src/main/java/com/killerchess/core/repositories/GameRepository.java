package com.killerchess.core.repositories;

import com.killerchess.core.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
//
//    Game save(Game game);
//    Game findOne(Integer gameId);
//    void delete(Game gameToDelete);
//    boolean exists(String primaryKey);
}