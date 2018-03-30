package com.killerchess.core.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Example Entity

@Entity
@Table(name = "user")
public class User {
    private String login;
    private String password;
    private SessionFactory sessionFactory;

    public User() {
    }

    public static void main(String[] args) {
        User test = new User();
        test.setup();
    }

    @Id
    @Column(name = "login")
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

    public void setup() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Session session = sessionFactory.openSession();

        String login = "test";
        User test = session.get(User.class, login);

        System.out.println(test.getLogin());
        System.out.println(test.getPassword());

        session.close();
    }
}
