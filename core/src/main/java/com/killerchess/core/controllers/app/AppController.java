package com.killerchess.core.controllers.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AppController {

    @RequestMapping(method = RequestMethod.GET, value = "/mainMenu")
    public void mainMenu() {
        //here need to show Main Menu of the game
        /*
        to consider:
        - we need somehow authenticate if current person can see "main menu" view.
        - we don't want someone just to enter our URL (i.e. http://killerchess.com/mainMenu) and be able to view it
            - one option is to check session (if the current person is logged in user etc)
            - second one is to pass parameters of user (username and password) as a function arguments and then check if he is authorized to see the Main Menu
         */
        
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gameRanking")
    public void gameRanking() {
        //here need to show ranking of the game
        /*
        to consider:
        - we need somehow authenticate if current person can see "ranking" view.
        - we don't want (OR MAYBE HERE WE WANT, CAUSE RANKING IS NOTHING important and everyone can see it)
          someone just to enter our URL (i.e. http://killerchess.com/ranking) and be able to view it
            - one option is to check session (if the current person is logged in user etc)
            - second one is to pass parameters of user (username and password) as a function arguments and then check if he is authorized to see the Ranking
         */
    }
}
