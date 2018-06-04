package com.killerchess.core.controllers.game;

import com.killerchess.core.exceptions.AuthenticationFailedException;
import com.killerchess.core.services.GameService;
import com.killerchess.core.services.UserService;
import com.killerchess.core.util.FieldNames;
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
    public static final String NEW_GAME_WITH_NAME_PATH = "/newGameWithName";

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_GAME_PATH)
    public ResponseEntity newGame(@RequestParam(value = "hostName") String hostName,
                                  @RequestParam(value = "guestName") String guestName,
                                  @RequestParam(value = "gameType") String gameType) {

        try {
            userService.isValidUser(hostName);
            userService.isValidUser(guestName);
        } catch (AuthenticationFailedException e) {
            return new ResponseEntity(e.getHttpStatusCode());
        }

        try {
            if (gameType.equals(FieldNames.PVP.getName())) {
                gameService.initNewGame(hostName, guestName);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_GAME_WITH_NAME_PATH)
    public ResponseEntity newGameWithName(@RequestParam(value = "gameId") String gameId,
                                          @RequestParam(value = "gameName") String gameName,
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

    @RequestMapping(method = RequestMethod.GET, value = "/gameBoard")
    public String gameBoard(Integer gameId, Integer gameStateNumber) {

        try {
            return gameService.getSpecificGameState(gameId, gameStateNumber);
        } catch (Exception e) {
            return "";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/gameBoard")
    public ResponseEntity gameBoard(Integer gameId, String gameState) {

        try {
            gameService.saveSpecificGameState(gameId, gameState);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
