package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.*;

@Entity
@Table(name = "ranking")
public class Ranking {

    private User user;
    private int points;
    private String userLogin;

    @Id
    public String getUserLogin() {
        return userLogin;
    }
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @OneToOne
    @MapsId
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "points")
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
