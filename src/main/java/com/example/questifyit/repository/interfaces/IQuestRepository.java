package com.example.questifyit.repository.interfaces;

import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import com.example.questifyit.repository.interfaces.crud.ICreateUpdateRepository;
import com.example.questifyit.repository.interfaces.crud.IReadOnlyRepository;

import java.util.UUID;

public interface IQuestRepository extends ICreateUpdateRepository<UUID, Quest>, IReadOnlyRepository<UUID,Quest> ,IRepository<UUID, Quest>{

}
