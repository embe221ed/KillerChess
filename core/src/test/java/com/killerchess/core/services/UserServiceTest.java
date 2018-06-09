package com.killerchess.core.services;

import com.killerchess.core.Core;
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
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private int initialNumberOfUsers;
    private ArrayList<String> usernames = new ArrayList<>();
    private boolean isClassConfigured;

    @Before
    public void setUp() {

        if (!isClassConfigured) {
            isClassConfigured = true;

            initialNumberOfUsers = userService.findAll().size();

            String usernamePrefix = "username";
            int usersToBeTestedCount = 3;
            IntStream.range(0, usersToBeTestedCount).forEach((val) -> usernames.add(usernamePrefix + UUID.randomUUID()));
        }

    }

    @Test
    public void whenUserRepositoryIsEmpty() {
        assertEquals(userService.findAll().size(), initialNumberOfUsers);
    }

    @Test
    public void whenUserRepositoryIsNotEmpty() {
        User user1 = new User(usernames.get(0));
        user1.setHashedPassword("Abcdef1!");

        User user2 = new User(usernames.get(1));
        user2.setHashedPassword("Abcdef1!");

        User user3 = new User(usernames.get(2));
        user3.setHashedPassword("Abcdef1!");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        assertEquals(userService.findAll().size(), initialNumberOfUsers + 3);
    }

    @Test
    public void whenUserIsSaved() {
        User user1 = new User(usernames.get(0));
        user1.setHashedPassword("Abcdef1!");

        userService.save(user1);

        assertEquals(userService.find(user1), user1);
        assertTrue(userService.existsLogin(usernames.get(0)));
    }

    @Test
    public void whenUserIsNotSaved() {
        User user1 = new User(usernames.get(0));
        user1.setHashedPassword("Abcdef1!");

        assertNull(userService.find(user1));
        assertFalse(userService.existsLogin(usernames.get(0)));
    }

}