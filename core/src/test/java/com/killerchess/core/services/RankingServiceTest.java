package com.killerchess.core.services;

import com.killerchess.core.Core;
import com.killerchess.core.game.RankingRegistry;
import com.killerchess.core.user.User;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private UserService userService;

    private int initialNumberOfUsersInRanking;
    private ArrayList<String> usernames = new ArrayList<>();
    private boolean isClassConfigured;
    private int usersToBeTestedCount = 3;
    private List<RankingRegistry> initialRanking;

    @Before
    public void setUp() {

        if (!isClassConfigured) {
            isClassConfigured = true;

            initialRanking = rankingService.findAllSorted(0);
            initialNumberOfUsersInRanking = initialRanking.size();

            String usernamePrefix = "username";
            IntStream.range(0, usersToBeTestedCount).forEach((val) -> usernames.add(usernamePrefix + UUID.randomUUID()));
        }

    }

    @Test
    public void whenRankingRepositoryIsEmpty() {
        assertEquals(rankingService.findAllSorted(0).size(), initialNumberOfUsersInRanking);
    }

    @Test
    public void whenRankingRepositoryIsNotEmpty() {
        for (String username : usernames) {
            User user = new User(username);
            userService.save(user);

            RankingRegistry rankingRegistry = new RankingRegistry();
            rankingRegistry.setUser(user);
            rankingRegistry.setUserLogin(username);
            rankingRegistry.setPoints(usernames.indexOf(username));
            rankingService.save(rankingRegistry);
        }

        for (int userId = 0; userId <= usersToBeTestedCount; userId++) {
            int finalUserId = userId;
            int initialUsersWithPointsGreaterThan = initialRanking.stream()
                    .filter(rankingRegistry -> rankingRegistry.getPoints() >= finalUserId)
                    .toArray().length;
            int expected = rankingService.findAllSorted(userId).size();
            int actual = initialUsersWithPointsGreaterThan + usersToBeTestedCount - userId;

            assertEquals(expected, actual);
        }
    }

    @Test
    public void whenUserDoesNotExists() {
        usernames.forEach(username -> assertNull(rankingService.findByUsername(username)));
    }

    @Test
    public void whenUserExists() {
        for (String username : usernames) {
            User user = new User(username);
            userService.save(user);

            RankingRegistry rankingRegistry = new RankingRegistry();
            rankingRegistry.setUser(user);
            rankingRegistry.setUserLogin(username);
            rankingRegistry.setPoints(usernames.indexOf(username));
            rankingService.save(rankingRegistry);

            assertEquals(rankingService.findByUsername(username), rankingRegistry);
        }
    }

}