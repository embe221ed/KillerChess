package com.killerchess.core.repositories;

import com.killerchess.core.game.RankingRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<RankingRegistry, String> {
    public List<RankingRegistry> findByUserLogin(String userLogin);

    public List<RankingRegistry> findAllByPointsGreaterThanOrderByPoints(int points);
}
