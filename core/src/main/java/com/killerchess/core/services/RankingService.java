package com.killerchess.core.services;

import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {
    private final RankingRepository rankingRepository;

    @Autowired
    RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public RankingRegistry findByUsername(String username) {
        return rankingRepository.findByUserLogin(username);
    }

    public void save(RankingRegistry rankingRegistry) {
        rankingRepository.save(rankingRegistry);
    }

    public List<RankingRegistry> findAllSorted(int points) {
        return rankingRepository.findAllByPointsGreaterThanEqualOrderByPointsDesc(points);
    }
}
