package com.killerchess.core.repositories;

import com.killerchess.core.game.RankingRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<RankingRegistry, String> {
}
