package com.killerchess.core.services;

import com.killerchess.core.dto.UserDTO;
import com.killerchess.view.View;
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
@SpringBootTest(classes = View.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void whenPasswordIsCorrect() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcdef1!");
        assertTrue(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordIsTooShort() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcde1!");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordIsTooLong() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcdefghijklmnopqrstuvwz1!");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordHasNoNumbers() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcdef!!");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordHasNoSpecialSigns() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcdef11");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordHasNoSmallLetters() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("ABCDEF1!");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordHasNoCapilats() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("abcdef1!");
        assertFalse(registerService.isValidUser(userDTO));
    }

    @Test
    public void whenPasswordHasSpace() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("Abcde 1!");
        assertFalse(registerService.isValidUser(userDTO));
    }

}