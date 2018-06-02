package com.killerchess.core.user;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Component
@Entity
@Table(name = "game_user")
public class User implements Serializable {

    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        if (!login.equals(that.login)) return false;
        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}