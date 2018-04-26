package com.killerchess.core.user;

import org.hibernate.SessionFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "game_user")
public class User implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        if (!login.equals(that.login)) return false;
        return login.equals(that.login);
    }
}