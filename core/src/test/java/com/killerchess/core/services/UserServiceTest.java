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
            int numberOfUsersToAdd = 3;
            IntStream.range(0, numberOfUsersToAdd).forEach((val) -> usernames.add(usernamePrefix + UUID.randomUUID()));
        }

    }

    @Test
    public void whenUserRepositoryIsEmpty() {
        assertEquals(userService.findAll().size(), initialNumberOfUsers);
    }

    @Test
    public void whenUserRepositoryIsNotEmpty() {
        User user1 = new User();
        user1.setLogin(usernames.get(0));
        user1.setPassword("Abcdef1!");

        User user2 = new User();
        user2.setLogin(usernames.get(1));
        user2.setPassword("Abcdef1!");

        User user3 = new User();
        user3.setLogin(usernames.get(2));
        user3.setPassword("Abcdef1!");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        assertEquals(userService.findAll().size(), initialNumberOfUsers + 3);
    }

    @Test
    public void whenUserIsSaved() {
        User user1 = new User();
        user1.setLogin(usernames.get(0));
        user1.setPassword("Abcdef1!");

        userService.save(user1);

        assertEquals(userService.find(user1), user1);
        assertTrue(userService.existsUser(user1));
    }

    @Test
    public void whenUserIsNotSaved() {
        User user1 = new User();
        user1.setLogin(usernames.get(0));
        user1.setPassword("Abcdef1!");

        assertNull(userService.find(user1));
        assertFalse(userService.existsUser(user1));
    }

}