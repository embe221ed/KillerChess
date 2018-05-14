package com.killerchess.core.controllers.game;

import com.killerchess.core.exceptions.AuthenticationFailedException;
import com.killerchess.core.response.api.ResponseMap;
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

import java.util.Optional;

@RestController
public class GameController {

    private static final String NEW_GAME_PATH = "/newGame";

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

        try{
            userService.isValidUser(hostName);
            userService.isValidUser(guestName);
        }
        catch(AuthenticationFailedException e){
            return new ResponseEntity(HttpStatus.resolve(e.getHttpStatusCode()));
        }

        if(gameType.equals("pvp")) {
            gameService.initNewGame(hostName, guestName);
        }

       return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/gameBoard")
    public void gameBoard() {
        //here need to show game board and somehow pass information about users who play the game
        /*
        to consider:
        - we need somehow authenticate if current person can see "gameBoard" view (he must be one of two players playing specific game)
        - we don't want someone just to enter our URL (i.e. http://killerchess.com/gameBoard) and be able to view it
            - one option is to check session (if the current person is logged in user etc)
            - second one is to pass parameters of user (username and password) as a function arguments and then check if he is authorized to see the gameBoard
         */
    }


}
