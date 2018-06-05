package com.killerchess.core.services;

import com.killerchess.core.Core;
import com.killerchess.core.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void whenPasswordIsCorrect() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcdef1!");
        assertTrue(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordIsTooShort() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcde1!");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordIsTooLong() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcdefghijklmnopqrstuvwz1!");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordHasNoNumbers() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcdef!!");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordHasNoSpecialSigns() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcdef11");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordHasNoSmallLetters() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("ABCDEF1!");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordHasNoCapilats() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("abcdef1!");
        assertFalse(registerService.isValidUser(user));
    }

    @Test
    public void whenPasswordHasSpace() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("Abcde 1!");
        assertFalse(registerService.isValidUser(user));
    }

}