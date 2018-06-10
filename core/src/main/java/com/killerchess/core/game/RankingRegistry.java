package com.killerchess.core.game;

import com.killerchess.core.user.User;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankingRegistry rankingRegistry = (RankingRegistry) o;
        return Objects.equals(user, rankingRegistry.user) &&
                Objects.equals(points, rankingRegistry.points) &&
                Objects.equals(userLogin, rankingRegistry.userLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, points, userLogin);
    }
}
