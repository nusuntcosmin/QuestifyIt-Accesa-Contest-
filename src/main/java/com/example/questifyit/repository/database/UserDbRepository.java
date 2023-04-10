package com.example.questifyit.repository.database;

import com.example.questifyit.domain.User;
import com.example.questifyit.repository.interfaces.IUserRepository;

import java.util.UUID;

public class UserDbRepository implements IUserRepository {
    @Override
    public void update(UUID entityID, User newEntity) {

    }

    @Override
    public void add(User entityToAdd) {

    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findOne(UUID entityID) {
        return null;
    }
}
