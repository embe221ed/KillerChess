package com.killerchess.core.controllers.app;

import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class AppController {

    private final UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gameRanking")
    public List<RankingRegistryDTO> gameRanking() {
        //sample, not finished
        return new ArrayList<RankingRegistryDTO>();
    }
}
