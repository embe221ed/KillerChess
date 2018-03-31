package com.killerchess.core.controllers.game;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class GameController {


    @RequestMapping(method = RequestMethod.GET, value = "/newGame")
    public void newGame(@RequestParam(value = "hostName") String hostName) {
        //here need to return view of creating the new game
        //
    }

    @RequestMapping(method = RequestMethod.POST, value = "/newGame")
    public void newGame(@RequestParam(value = "hostName") String hostName,
                        @RequestParam(value = "guestName") String guestName,
                        @RequestParam(value = "gameType") String gameType) {
        //here need to create new Game in DB and invoke gameBoard method from this controller in order to start the game
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
