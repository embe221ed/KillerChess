package com.killerchess.core.controllers.app;

import com.killerchess.core.chessboard.points.counter.PointsCounter;
import com.killerchess.core.controllers.game.GameController;
import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.game.Game;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.services.GameService;
import com.killerchess.core.services.RankingService;
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
    public static final String UPDATE_USER_RANKING_STALEMATE_PATH = "/updateRankingStalemate";

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
            String losersLogin = session.getAttribute("username").toString();
            var gameStates = gameService.getListOfGameStatesForGame(gameId);
            Game game = gameService.findGame(gameId);
            String winnersLogin =
                    game.getHost().getLogin().equals(losersLogin)
                            ? game.getGuest().getLogin()
                            : game.getHost().getLogin();
            String firstGameState = gameStates.get(gameStates.size() - 1);
            String lastGameState = gameStates.get(0);
            updateUsersRankingPoints(winnersLogin, firstGameState, lastGameState, winnersLogin.equals(game.getHost().getLogin()));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = UPDATE_USER_RANKING_STALEMATE_PATH)
    public ResponseEntity updateUsersRankingPointsStalemate(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GameController.GAME_ID_PARAM).toString();
            Game game = gameService.findGame(gameId);
            String hostUsername = game.getHost().getLogin();
            String guestUsername = game.getGuest().getLogin();
            var gameStates = gameService.getListOfGameStatesForGame(gameId);
            String firstGameState = gameStates.get(gameStates.size() - 1);
            String lastGameState = gameStates.get(0);
            updateUsersRankingPoints(hostUsername, firstGameState, lastGameState, true);
            updateUsersRankingPoints(guestUsername, firstGameState, lastGameState, false);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateUsersRankingPoints(String username, String firstGameState, String lastGameState, boolean isHost) {
        RankingRegistry rankingRegistry = rankingService.findByUsername(username);
        int userPoints = rankingRegistry.getPoints();
        userPoints +=
                (isHost)
                        ? pointsCounter.countWhitePlayerPoints(firstGameState, lastGameState)
                        : pointsCounter.countBlackPlayerPoints(firstGameState, lastGameState);
        rankingRegistry.setPoints(userPoints);
        rankingService.save(rankingRegistry);
    }
}
