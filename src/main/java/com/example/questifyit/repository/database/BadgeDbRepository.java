package com.example.questifyit.repository.database;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.repository.interfaces.IBadgeRepository;

import java.util.UUID;

public class BadgeDbRepository implements IBadgeRepository {
    @Override
    public Iterable<Badge> findAll() {
        return null;
    }

    @Override
    public Badge findOne(UUID entityID) {
        return null;
    }
}
