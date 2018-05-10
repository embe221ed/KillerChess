package com.killerchess.core.chessboard;

import org.junit.Test;

/*
@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")*/
public class UserControllerTest {

    @Test
    public void thisMethodExistsOnlyForDebugReasons(){

    }
/*
    @Autowired
    private MockMvc mvc;



    @Test
    public void simpleSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/register")
                .param("Username", "michal")
                .param("Password", "matuszewski"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("Success").value(true))
                .andExpect(jsonPath("ErrorCode").value(0))
                .andExpect(jsonPath("Message").value(""))
                .andExpect(jsonPath("Register.Username").value("michal"))
                .andExpect(jsonPath("Register.Password").value("matuszewski"));
    }

    @Test
    public void simpleFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reporting/details")
                .param("Username", "michal")
                .param("Password", "OKON"))
                .andExpect(status().is(403));
    }*/
}
