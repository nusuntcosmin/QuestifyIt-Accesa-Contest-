package com.example.questifyit.repository.interfaces;

import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.repository.interfaces.crud.ICreateUpdateRepository;
import com.example.questifyit.repository.interfaces.crud.IReadOnlyRepository;
import com.example.questifyit.utils.data_structures.Pair;

import java.util.UUID;

public interface IUserBadgesRepository extends IReadOnlyRepository<Pair<UUID>, UserBadges>, ICreateUpdateRepository<Pair<UUID>,UserBadges>,IRepository<Pair<UUID>,UserBadges>{

}
