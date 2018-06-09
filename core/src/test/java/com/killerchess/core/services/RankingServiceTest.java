package com.killerchess.core.services;

import com.killerchess.core.Core;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Core.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Test
    public void simpleTest() {
        assertTrue(true);
    }

}