package com.killerchess.core.controllers.user;

import com.killerchess.core.Core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
@Rollback
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenRegistrationSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "jan")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));
    }

    @Test
    public void whenRegistrationFailBecausePasswordIsWrong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "janek")
                .param("password", "wrongPassword"))
                .andExpect(status().is(406));
    }

    @Test
    public void whenRegistrationFailBecauseLoginIsTaken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "tomek")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "tomek")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(406));
    }

    @Test
    public void whenLogingSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "olaf")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));

        mvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "olaf")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));
    }

    @Test
    public void whenLogingFailBecausePasswordIsWrong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "michal")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));

        mvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "michal")
                .param("password", "wrongPassword"))
                .andExpect(status().is(406));

    }

    @Test
    public void whenLogingFailBecauseUserDoesNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "pawel")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(406));
    }
}
