package com.killerchess.core.controllers.user;

import com.killerchess.view.View;
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
@SpringBootTest(classes = View.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void simpleSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/register")
                .param("Username", "michal")
                .param("Password", "matuszewski"))
                .andExpect(status().is(200));
    }

    @Test
    public void simpleFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reporting/details")
                .param("Username", "michal")
                .param("Password", "OKON"))
                .andExpect(status().is(403));
    }
}
