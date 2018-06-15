package com.killerchess.core.controllers.game;

import com.killerchess.core.chessmans.ChessmanColourEnum;
import com.killerchess.core.dto.GameDTO;
import com.killerchess.core.game.Game;
import com.killerchess.core.game.GameState;
import com.killerchess.core.services.GameService;
import com.killerchess.core.services.UserService;
import com.killerchess.core.user.User;
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
    public static final String FIRST_GAME_STATE_PATH = "/addFirstGameState";
    public static final String IS_USERS_MOVE_PATH = "/isUsersMove";
    public static final String NEW_STATE_PATH = "/newState";
    public static final String CHECK_GUEST_PATH = "/checkGuest";
    public static final String JOIN_GAME_PATH = "/joinGame";
    public static final String GAME_STATE_CHANGED_PATH = "/gameStateChanged";
    public static final String GET_COLOR_PATH = "/getColor";
    public static final String FINISH_GAME_PATH = "/finishGame";
    public static final String STATE_PARAM = "state";
    public static final String GAME_ID_PARAM = "gameId";
    public static final String GAME_NAME_PARAM = "gameName";
    public static final String GAME_STATE_NUMBER_PARAM = "gameStateNumber";
    public static final String USERNAME_PARAM = "username";
    public static final String USERNAME_ATTRIBUTE = "username";

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
        var username = session.getAttribute(USERNAME_PARAM).toString();
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

    @RequestMapping(method = RequestMethod.POST, value = FIRST_GAME_STATE_PATH)
    public ResponseEntity<Integer> addFirstGameState(@RequestParam(value = STATE_PARAM) String gameState,
                                                     HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            boolean mockGuestMove = false;
            GameState gameStateObject = gameService.saveSpecificGameState(gameId, gameState, mockGuestMove);
            return new ResponseEntity<>(gameStateObject.getGameStateNumber(), HttpStatus.OK);
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
            String username = session.getAttribute(USERNAME_ATTRIBUTE).toString();
            Game game = gameService.findGame(gameId);
            boolean move = username.equals(game.getHost().getLogin());
            GameState gameStateObject = gameService.saveSpecificGameState(gameId, gameState, move);
            return new ResponseEntity<>(gameStateObject.getGameStateNumber(), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = GET_COLOR_PATH)
    public ResponseEntity<ChessmanColourEnum> getUsersColor(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            String username = session.getAttribute(USERNAME_ATTRIBUTE).toString();
            Game game = gameService.findGame(gameId);
            return game.getHost().getLogin().equals(username)
                    ? new ResponseEntity<>(ChessmanColourEnum.WHITE, HttpStatus.OK)
                    : new ResponseEntity<>(ChessmanColourEnum.BLACK, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = GAME_BOARD_PATH)
    public ResponseEntity<String> getLastGameState(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            String gameState = gameService.getLastGameStateForGame(gameId).getState();
            Game game = gameService.findGame(gameId);
            if (game.getGameFinished())
                return new ResponseEntity<>(gameState, HttpStatus.CREATED);
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
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            GameState gameState = gameService.getLastGameStateForGame(gameId);
            if (!gameStateNumber.equals(gameState.getGameStateNumber())) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = FINISH_GAME_PATH)
    public ResponseEntity finishGame(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            Game game = gameService.findGame(gameId);
            game.setGameFinished(true);
            gameService.updateGame(game);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = IS_USERS_MOVE_PATH)
    public ResponseEntity<Boolean> isUsersMove(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String username = session.getAttribute(USERNAME_ATTRIBUTE).toString();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            Game game = gameService.findGame(gameId);
            if (game.getGameFinished()) {
                return new ResponseEntity<>(false, HttpStatus.CREATED);
            }
            GameState gameState = gameService.getLastGameStateForGame(gameId);
            boolean isUsersMove = gameState.getMove() ^ username.equals(gameState.getGame().getHost().getLogin());
            return new ResponseEntity<>(isUsersMove, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = JOIN_GAME_PATH)
    public ResponseEntity<Integer> joinGame(@RequestParam(value = GAME_ID_PARAM) String gameId,
                                            HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String username = session.getAttribute(USERNAME_ATTRIBUTE).toString();
            Game game = gameService.findGame(gameId);
            if (game == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = new User();
            user.setLogin(username);
            game.setGuest(user);
            gameService.updateGame(game);
            session.setAttribute(GAME_ID_PARAM, gameId);
            GameState gameState = gameService.getLastGameStateForGame(gameId);
            return new ResponseEntity<>(gameState.getGameStateNumber(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = CHECK_GUEST_PATH)
    public ResponseEntity<Boolean> checkIfGuestIsPresent(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            String gameId = session.getAttribute(GAME_ID_PARAM).toString();
            Game game = gameService.findGame(gameId);
            boolean isGuestPresent = game.getGuest() != null;
            return new ResponseEntity<>(isGuestPresent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
