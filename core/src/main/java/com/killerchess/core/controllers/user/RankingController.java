package com.killerchess.core.controllers.user;

import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RankingController {

    private static final String RANKING_PATH = "/ranking";

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @RequestMapping(method = RequestMethod.GET, value = RANKING_PATH)
    public ResponseEntity<List> getRanking() {
        List<RankingRegistry> rankingRegistries = rankingService.findAllSorted(0);
        List<RankingRegistryDTO> rankingRegistryDTOS = new ArrayList<>();
        for (RankingRegistry rankingRegistry : rankingRegistries) {
            RankingRegistryDTO tempRankingRegistryDTO = new RankingRegistryDTO();
            tempRankingRegistryDTO.setPoints(rankingRegistry.getPoints());
            tempRankingRegistryDTO.setUsername(rankingRegistry.getUserLogin());
            rankingRegistryDTOS.add(tempRankingRegistryDTO);
        }
        return new ResponseEntity<>(rankingRegistryDTOS, HttpStatus.OK);
    }
}
