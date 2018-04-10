package com.killerchess.core.user;

import org.hibernate.SessionFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Example Entity

@Entity
@Table(name = "game_user")
public class User {
    private String login;
    private String password;
    private SessionFactory sessionFactory;

    @Id
    @Column(name = "login")
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}