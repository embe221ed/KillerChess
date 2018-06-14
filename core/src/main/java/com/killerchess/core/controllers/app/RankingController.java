package com.killerchess.core.controllers.app;

import com.killerchess.core.chessboard.points.counter.PointsCounter;
import com.killerchess.core.controllers.game.GameController;
import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.game.Game;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.services.GameService;
import com.killerchess.core.services.RankingService;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RankingController {

    public static final String RANKING_PATH = "/ranking";
    public static final String GET_USER_RANKING_PATH = "/getUserRanking";
    public static final String UPDATE_USER_RANKING_PATH = "/updateRanking";

    private final RankingService rankingService;
    private final GameService gameService;
    private final PointsCounter pointsCounter;

    @Autowired
    public RankingController(RankingService rankingService, GameService gameService, PointsCounter pointsCounter) {
        this.rankingService = rankingService;
        this.gameService = gameService;
        this.pointsCounter = pointsCounter;
    }

    @RequestMapping(method = RequestMethod.GET, value = RANKING_PATH)
    public ResponseEntity<List<RankingRegistryDTO>> getRanking() {
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

    @RequestMapping(method = RequestMethod.GET, value = GET_USER_RANKING_PATH)
    public ResponseEntity<RankingRegistryDTO> getUserRanking(HttpServletRequest request) {
        try {
            String username = request.getSession().getAttribute("username").toString();
            RankingRegistry rankingRegistry = rankingService.findByUsername(username);
            RankingRegistryDTO rankingRegistryDTO = new RankingRegistryDTO();
            rankingRegistryDTO.setUsername(rankingRegistry.getUserLogin());
            rankingRegistryDTO.setPoints(rankingRegistry.getPoints());
            return new ResponseEntity<>(rankingRegistryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = UPDATE_USER_RANKING_PATH)
    public ResponseEntity updateUserRankingPoints(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GameController.GAME_ID_PARAM).toString();
            Game game = gameService.findGame(gameId);
            User host = game.getHost();
            User guest = game.getGuest();
            var gameStates = gameService.getListOfGameStatesForGame(gameId);
            String firstGameState = gameStates.get(gameStates.size() - 1);
            String lastGameState = gameStates.get(0);
            RankingRegistry hostRankingRegistry = rankingService.findByUsername(host.getLogin());
            RankingRegistry guestRankingRegistry = rankingService.findByUsername(guest.getLogin());
            int hostPoints = hostRankingRegistry.getPoints();
            int guestPoints = guestRankingRegistry.getPoints();
            hostPoints += pointsCounter.countWhitePlayerPoints(firstGameState, lastGameState);
            guestPoints += pointsCounter.countBlackPlayerPoints(firstGameState, lastGameState);
            hostRankingRegistry.setPoints(hostPoints);
            guestRankingRegistry.setPoints(guestPoints);
            rankingService.save(hostRankingRegistry);
            rankingService.save(guestRankingRegistry);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
