package com.killerchess.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.killerchess.core.controllers",
        "com.killerchess.core.services",
        "com.killerchess.core.chessboard"})
@EnableJpaRepositories({"com.killerchess.core.repositories"})
@EntityScan({"com.killerchess.core"})
public class Core {
    public static void main(String[] args) {
        SpringApplication.run(Core.class, args);
    }
}
