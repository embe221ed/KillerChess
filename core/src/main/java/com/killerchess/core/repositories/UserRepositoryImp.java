package com.killerchess.core.repositories;

import com.killerchess.core.user.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImp<User, String> implements UserRepository<User, String> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(User newUser) {
        return null;
    }

    @Override
    public User findOne(String id) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public void delete(User userToDelete) {

    }

    @Override
    public boolean exists(String primaryKey) {
        return false;
    }
}
