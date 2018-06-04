package com.killerchess.core.controllers.state;

import com.killerchess.core.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.killerchess.core.controllers.game.GameController.GAME_ID_PARAM;

@RestController
public class GameStateController {


    public static final String NEW_STATE_PATH = "/newState";
    public static final String STATE_PARAM = "state";

    private GameService gameService;

    @Autowired
    public GameStateController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_STATE_PATH)
    public ResponseEntity newState(@RequestParam(value = STATE_PARAM) String state,
                                   @RequestParam(value = GAME_ID_PARAM) String gameId) {
        gameService.saveSpecificGameState(gameId, state);
        return new ResponseEntity(HttpStatus.OK);
    }
}
