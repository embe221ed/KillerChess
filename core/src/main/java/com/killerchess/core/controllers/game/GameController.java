package com.killerchess.core.controllers.game;

import com.killerchess.core.dto.GameDTO;
import com.killerchess.core.game.Game;
import com.killerchess.core.game.GameState;
import com.killerchess.core.services.GameService;
import com.killerchess.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
public class GameController {

    public static final String NEW_GAME_PATH = "/newGame";
    public static final String GAME_BOARD_PATH = "/gameBoard";
    public static final String GAME_BOARD_LIST_PATH = "/listOfGameStates";
    public static final String AVAILABLE_GAMES_PATH = "/availableGames";
    public static final String NEW_STATE_PATH = "/newState";
    public static final String GAME_STATE_CHANGED_PATH = "/gameStateChanged";
    public static final String STATE_PARAM = "state";
    public static final String GAME_ID_PARAM = "gameId";
    public static final String GAME_NAME_PARAM = "gameName";
    public static final String GAME_STATE_PARAM = "gameState";
    public static final String GAME_STATE_NUMBER_PARAM = "gameStateNumber";
    public static final String USER_NAME_PARAM = "username";

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_GAME_PATH)
    public ResponseEntity newGameWithName(@RequestParam(value = GAME_ID_PARAM) String gameId,
                                          @RequestParam(value = GAME_NAME_PARAM, required = false, defaultValue = "game") String gameName,
                                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        var username = session.getAttribute(USER_NAME_PARAM).toString();
        if (userService.existsLogin(username)) {
            session.setAttribute(GAME_ID_PARAM, gameId);
            session.setAttribute(GAME_NAME_PARAM, gameName);
            gameService.initNewGame(gameId, gameName, username);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.GET, value = GAME_BOARD_LIST_PATH)
    public ResponseEntity<List<String>> listOfGameStatesForGame(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            List<String> gameStatesForGame = gameService.getListOfGameStatesForGame(gameId);
            return new ResponseEntity<>(gameStatesForGame, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_STATE_PATH)
    public ResponseEntity<Integer> saveNewGameState(@RequestParam(value = STATE_PARAM) String gameState,
                                                    HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            GameState gameStateObject = gameService.saveSpecificGameState(gameId, gameState);
            return new ResponseEntity<>(gameStateObject.getGameStateNumber(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = GAME_BOARD_PATH)
    public ResponseEntity<String> getLastGameState(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameState = gameService.getLastGameStateForGame(
                    session
                            .getAttribute(GAME_ID_PARAM)
                            .toString())
                    .getState();
            return new ResponseEntity<>(gameState, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = AVAILABLE_GAMES_PATH)
    public ResponseEntity<List<GameDTO>> getAvailableGames() {
        try {
            List<Game> availableGamesList = gameService.findAvailableGames();
            List<GameDTO> availableGamesDTOS = new ArrayList<>();
            for (var availableGame : availableGamesList) {
                GameDTO availableGameDTO = new GameDTO();
                availableGameDTO.setGameId(availableGame.getGameId());
                availableGameDTO.setGameName(availableGame.getGameName());
                availableGameDTO.setHost(availableGame.getHost().getLogin());
                availableGamesDTOS.add(availableGameDTO);
            }
            return new ResponseEntity<>(availableGamesDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = GAME_STATE_CHANGED_PATH)
    public ResponseEntity<Boolean> isGameStateChanged(
            @RequestParam(value = GAME_STATE_NUMBER_PARAM) Integer gameStateNumber,
            HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            GameState gameState = gameService.getLastGameStateForGame(session.getAttribute(GAME_ID_PARAM).toString());
            if (!gameStateNumber.equals(gameState.getGameStateNumber())) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
