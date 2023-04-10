package com.example.questifyit.repository.interfaces;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.User;
import com.example.questifyit.repository.interfaces.crud.IReadOnlyRepository;

import java.util.UUID;

public interface IBadgeRepository extends IReadOnlyRepository<UUID, Badge>,IRepository<UUID, Badge> {

}
