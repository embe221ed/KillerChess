package com.killerchess.core.repositories;

import com.killerchess.core.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Game findByGameId(String id);

    List<Game> findAllByGuestIsNull();

}