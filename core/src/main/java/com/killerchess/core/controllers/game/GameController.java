package com.killerchess.core.controllers.game;

import com.killerchess.core.exceptions.AuthenticationFailedException;
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


@RestController
public class GameController {

    public static final String NEW_GAME_PATH = "/newGame";
    public static final String GAME_BOARD_PATH = "/gameBoard";
    public static final String GAME_ID_PARAM = "gameId";
    public static final String GAME_NAME_PARAM = "gameName";
    public static final String GAME_STATE_PARAM = "gameState";
    public static final String GAME_STATE_NUMBER_PARAM = "gameStateNumber";

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_GAME_PATH)
    public ResponseEntity newGameWithName(@RequestParam(value = GAME_ID_PARAM) String gameId,
                                          @RequestParam(value = GAME_NAME_PARAM) String gameName,
                                          HttpServletRequest request) {
        var username = request.getSession().getAttribute("username").toString();
        try {
            userService.isValidUser(username);
        } catch (AuthenticationFailedException e) {
            return new ResponseEntity(e.getHttpStatusCode());
        }
        gameService.initNewGame(gameId, gameName, username);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = GAME_BOARD_PATH)
    public String gameBoard(@RequestParam(value = GAME_ID_PARAM) String gameId,
                            @RequestParam(value = GAME_STATE_NUMBER_PARAM) Integer gameStateNumber) {
        //TODO trzeba zaimplementować.
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "GAME_BOARD_PATH")
    public ResponseEntity gameBoard(@RequestParam(value = GAME_ID_PARAM) String gameId,
                                    @RequestParam(value = GAME_STATE_PARAM) String gameState) {
        try {
            gameService.saveSpecificGameState(gameId, gameState);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
