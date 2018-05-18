package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    private Integer gameId;
    private User host;
    private User guest;

    @Id
    @Column(name = "gameId")
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer id) {
        this.gameId = id;
    }

    @ManyToOne
    @JoinColumn(name = "host")
    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    @ManyToOne
    @JoinColumn(name = "guest")
    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return gameId.equals (((Game) o).gameId);
    }
}