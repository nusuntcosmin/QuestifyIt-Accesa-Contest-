package com.example.questifyit.repository.database;

import com.example.questifyit.domain.Quest;
import com.example.questifyit.repository.interfaces.IQuestRepository;

import java.util.UUID;

public class QuestDbRepository implements IQuestRepository {
    @Override
    public void update(UUID entityID, Quest newEntity) {

    }

    @Override
    public void add(Quest entityToAdd) {

    }

    @Override
    public Iterable<Quest> findAll() {
        return null;
    }

    @Override
    public Quest findOne(UUID entityID) {
        return null;
    }
}
