package com.killerchess.core.services;

import com.killerchess.core.Core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void whenPasswordIsCorrect() {
        var password = "Abcdef1!";
        assertTrue(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordIsTooShort() {
        var password = "Abcde1!";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordIsTooLong() {
        var password = "Abcdefghijklmnopqrstuvwz1!";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordHasNoNumbers() {
        var password = "Abcdef!!";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordHasNoSpecialSigns() {
        var password = "Abcdef11";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordHasNoSmallLetters() {
        var password = "ABCDEF1!";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordHasNoCapilats() {
        var password = "abcdef1!";
        assertFalse(registerService.isPasswordValid(password));
    }

    @Test
    public void whenPasswordHasSpace() {
        var password = "Abcde 1!";
        assertFalse(registerService.isPasswordValid(password));
    }

}