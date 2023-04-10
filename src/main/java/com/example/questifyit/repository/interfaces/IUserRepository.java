package com.example.questifyit.repository.interfaces;

import com.example.questifyit.domain.User;
import com.example.questifyit.repository.interfaces.crud.ICreateUpdateRepository;
import com.example.questifyit.repository.interfaces.crud.IReadOnlyRepository;

import java.util.UUID;

public interface IUserRepository extends IReadOnlyRepository<UUID, User>, ICreateUpdateRepository<UUID,User> ,IRepository<UUID,User>{

}
