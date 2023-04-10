package com.example.questifyit.repository.database.factory;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.repository.interfaces.IRepository;
import com.example.questifyit.utils.Pair;

import java.util.UUID;

public interface IRepositoryFactory {

    IRepository<UUID, User> getUserRepository();
    IRepository<UUID, Badge> getBadgeRepository();
    IRepository<UUID, Quest> getQuestRepository();
    IRepository<Pair<UUID>, UserBadges> getUserBadgesRepository();

}
