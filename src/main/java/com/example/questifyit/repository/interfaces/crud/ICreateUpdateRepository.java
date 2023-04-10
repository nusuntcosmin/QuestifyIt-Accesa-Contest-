package com.example.questifyit.repository.interfaces.crud;

import com.example.questifyit.domain.Entity;

public interface ICreateUpdateRepository<ID, E extends Entity<ID>> {
    void update(ID entityID, E newEntity);
    void add(E entityToAdd);
}
