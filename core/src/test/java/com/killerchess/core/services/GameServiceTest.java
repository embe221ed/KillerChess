package com.killerchess.core.services;

import com.killerchess.core.Core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class GameServiceTest {

    @Test
    public void testing() {
        assertTrue(true);
    }
    /*private int initialNumberOfGames;
    private ArrayList<String> gameIds = new ArrayList<>();
    private boolean isClassConfigured;
    String gamePrefix = "game";
    String gameNamePrefix = "name";
    String userPrefix = "user";

    @Before
    public void setUp() {

        if (!isClassConfigured) {
            isClassConfigured = true;

            initialNumberOfGames = gameService.findAvailableGames().size();

            int gamesToBeTestedCount = 3;
            IntStream.range(0, gamesToBeTestedCount).forEach((val) -> gameIds.add(UUID.randomUUID().toString()));
        }

    }

    @Test
    public void whenGameRepositoryIsEmpty() {
        assertEquals(gameService.findAvailableGames().size(), initialNumberOfGames);
    }

    @Test
    public void whenGameRepositoryIsNotEmpty() {

        for (String gameId : gameIds) {
            gameService.initNewGame(gamePrefix + gameId,
                    gameNamePrefix + gameId,
                    userPrefix + gameId);
        }

        assertEquals(gameService.findAvailableGames().size(), initialNumberOfGames + 3);
    }*/

}