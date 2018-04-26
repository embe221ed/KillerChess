package com.killerchess.core.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class Game {

    private Integer game_id;
    private String host;
    private String guest;

    @Id
    @Column(name = "game_id")
    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer id) {
        this.game_id = id;
    }

    @Column(name = "host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Column(name = "guest")
    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return game_id.equals (((Game) o).game_id);
    }
}