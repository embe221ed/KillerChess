package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.*;

@Entity
@Table(name = "ranking")
public class RankingRegistry {

    private User user;
    private int points;
    private String userLogin;

    @Id
    @Column(name = "user_login")
    public String getUserLogin() {
        return userLogin;
    }
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @MapsId
    @OneToOne
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
        this.userLogin = user.getLogin();
    }

    @Column(name = "points")
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
