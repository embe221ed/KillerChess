package com.killerchess.core.controllers.user;

import com.killerchess.core.Core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void simpleSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "jan")
                .param("password", "Kowalsky9@"))
                .andExpect(status().is(200));
    }

    @Test
    public void simpleFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "jan")
                .param("password", "wrongpassword"))
                .andExpect(status().is(406));
    }
}
