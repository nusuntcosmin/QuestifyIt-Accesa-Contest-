package com.example.questifyit.repository.interfaces.crud;

import com.example.questifyit.domain.Entity;

import java.io.Serializable;

public interface IReadOnlyRepository<ID, E extends Entity<ID>>  {

    Iterable<E> findAll(); //returns all entities
    E findOne(ID entityID); // returns the entity with the given ID

}
