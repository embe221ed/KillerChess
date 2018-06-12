package com.killerchess.core.user;

import com.killerchess.core.user.crypter.Crypter;
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

    @Column(name = "salt")
    private String salt;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setHashedPassword(String password) {
        String salt = Crypter.getSalt();
        setHashedPassword(password, salt);
    }

    public void setHashedPassword(String password, String salt) {
        String hashedPassword = Crypter.getHashedPassword(password, salt);
        setPassword(hashedPassword);
        setSalt(salt);
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    private void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password, salt);
    }
}