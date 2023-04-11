package com.example.questifyit.repository.database.factory;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.repository.database.BadgeDbRepository;
import com.example.questifyit.repository.database.QuestDbRepository;
import com.example.questifyit.repository.database.UserBadgesDbRepository;
import com.example.questifyit.repository.database.UserDbRepository;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import com.example.questifyit.repository.interfaces.IRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import com.example.questifyit.utils.data_structures.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class RepositoryDbFactory implements IRepositoryFactory {

    private static final RepositoryDbFactory instanceOfRepositoryDbFactory = new RepositoryDbFactory();


    private final Properties databaseProperties = new Properties();

    private Logger log;

    public static RepositoryDbFactory getInstanceOfRepositoryDbFactory() {
        return instanceOfRepositoryDbFactory;
    }

    private RepositoryDbFactory() {
        try {
            log = LogManager.getLogger();
            log.info("Creating the instance of Repository Database Factory");
            databaseProperties.load(new FileReader("database.config"));
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public IRepository<UUID, User> getUserRepository() {
        log.traceEntry("Getting UserDbRepository instance");
        UserDbRepository userDbRepository = new UserDbRepository(databaseProperties);
        log.traceExit(userDbRepository);
        return userDbRepository;
    }

    @Override
    public IRepository<UUID, Badge> getBadgeRepository() {
        log.traceEntry("Getting BadgeDbRepository instance");
        BadgeDbRepository badgeDbRepository =  new BadgeDbRepository(databaseProperties);
        log.traceExit(badgeDbRepository);

        return badgeDbRepository;
    }

    @Override
    public IRepository<UUID, Quest> getQuestRepository() {
        log.traceEntry("Getting QuestDbRepository instance");
        IRepository<UUID,Quest> questRepository = new QuestDbRepository(getUserRepository(),databaseProperties);
        log.traceExit(questRepository);

        return questRepository;
    }

    @Override
    public IRepository<Pair<UUID>, UserBadges> getUserBadgesRepository() {
        log.traceEntry("Getting UserBadgesDbRepository instance");
        IRepository<Pair<UUID>, UserBadges> userBadgesIRepository = new UserBadgesDbRepository(databaseProperties, (IUserRepository) getUserRepository(), (IBadgeRepository) getBadgeRepository());
        log.traceExit(userBadgesIRepository);

        return userBadgesIRepository;
    }
}
