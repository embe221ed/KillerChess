package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "game")
public class Game {

    private Integer game_id;
    private User host;
    private User guest;

    @Id
    @Column(name = "game_id")
    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer id) {
        this.game_id = id;
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
    public User getGuest() { return guest; }
    public void setGuest(User guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return game_id.equals (((Game) o).game_id);
    }
}