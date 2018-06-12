package com.killerchess.core.repositories;

import com.killerchess.core.game.Game;
import com.killerchess.core.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStateRepository extends JpaRepository<GameState, Integer> {
    List<GameState> getGameStatesByGameOrderByGameStateNumberDesc(Game game);
}
