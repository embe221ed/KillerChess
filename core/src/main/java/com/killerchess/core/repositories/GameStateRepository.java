package com.killerchess.core.repositories;

import com.killerchess.core.game.GameState;
import com.killerchess.core.game.GameStateIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateRepository extends JpaRepository<GameState, GameStateIdentity> {

//    GameState save(GameState gameState);
//    GameState findOne(GameStateIdentity gameStateIdentity);
}
