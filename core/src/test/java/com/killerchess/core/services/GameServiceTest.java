package com.killerchess.core.services;

import com.killerchess.core.Core;
import com.killerchess.core.exceptions.GameNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class GameServiceTest {

    @Autowired
    private GameService gameService;
    private int initialNumberOfGames;
    private int gamesToBeTestedCount = 3;
    private ArrayList<String> gameIds = new ArrayList<>();
    private boolean isClassConfigured;
    private String gamePrefix = "game";
    private String gameNamePrefix = "name";
    private String userPrefix = "user";

    @Before
    public void setUp() {

        if (!isClassConfigured) {
            isClassConfigured = true;

            initialNumberOfGames = gameService.findAvailableGames().size();

            IntStream.range(0, gamesToBeTestedCount).forEach((val) -> gameIds.add(UUID.randomUUID().toString()));
        }

    }

    @Test
    public void whenGameRepositoryIsEmpty() {
        assertEquals(gameService.findAvailableGames().size(), initialNumberOfGames);
    }

    @Test
    public void whenGameRepositoryIsNotEmpty() {

        gameIds.forEach(gameId -> gameService.initNewGame(gamePrefix + gameId,
                gameNamePrefix + gameId,
                userPrefix + gameId));

        assertEquals(gameService.findAvailableGames().size(), initialNumberOfGames + gamesToBeTestedCount);
    }

    @Test
    public void whenGameStateRepositoryIsEmptyAndGameDoesNotExist() {
        assertEquals(gameService.getListOfGameStatesForGame(gamePrefix + gameIds.get(0)).size(), 0);
    }

    @Test
    public void whenGameStateRepositoryIsEmptyAndGameExist() {
        for (String gameId : gameIds) {
            gameService.initNewGame(gamePrefix + gameId,
                    gameNamePrefix + gameId,
                    userPrefix + gameId);

            assertEquals(gameService.getListOfGameStatesForGame(gamePrefix + gameId).size(), 0);
        }
    }

    @Test
    public void whenGameStateRepositoryIsNotEmpty() throws GameNotFoundException {

        for (String gameId : gameIds) {
            gameService.initNewGame(gamePrefix + gameId,
                    gameNamePrefix + gameId,
                    userPrefix + gameId);
            gameService.saveSpecificGameState(gamePrefix + gameId, "gameState");

            assertEquals(gameService.getListOfGameStatesForGame(gamePrefix + gameId).size(), 1);

        }
    }

}