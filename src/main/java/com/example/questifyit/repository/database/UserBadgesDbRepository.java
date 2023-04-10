package com.example.questifyit.repository.database;
import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.repository.database.jdbc_utils.JdbcUtils;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import com.example.questifyit.repository.interfaces.IUserBadgesRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import com.example.questifyit.utils.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class UserBadgesDbRepository implements IUserBadgesRepository {

    private JdbcUtils jdbcUtils;

    public UserBadgesDbRepository(Properties properties,IUserRepository userRepository, IBadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        jdbcUtils = new JdbcUtils(properties);
    }
    private IUserRepository userRepository;
    private IBadgeRepository badgeRepository;
    private final Logger log = LogManager.getLogger();
    @Override
    public void update(Pair<UUID> entityID, UserBadges newEntity) {
        log.traceEntry("Updating many to many table userbadges with id{} with new entity",entityID,newEntity);
        Connection con = jdbcUtils.getConnection();
        String UPDATE_USERBADGES_QUERRY = "UPDATE userbadges SET achieved = ? WHERE uid = ? AND bid = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USERBADGES_QUERRY)){
            preparedStatement.setObject(2,entityID.getFirst());
            preparedStatement.setObject(3,entityID.getSecond());
            preparedStatement.setBoolean(1, newEntity.getAchieved());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }

    @Override
    public void add(UserBadges entityToAdd) {
        log.traceEntry("Saving many to many table value entity {}",entityToAdd);
        Connection con = jdbcUtils.getConnection();
        String ADD_USER_BADGES_QUERRY = "INSERT INTO userbadges VALUES(?, ?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(ADD_USER_BADGES_QUERRY)){
            preparedStatement.setObject(1,entityToAdd.getEntityID().getFirst());
            preparedStatement.setObject(2,entityToAdd.getEntityID().getSecond());
            preparedStatement.setBoolean(3,entityToAdd.getAchieved());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }
    @Override
    public Iterable<UserBadges> findAll() {
        log.traceEntry("Selecting all the userbadges");
        Connection con = jdbcUtils.getConnection();
        List<UserBadges> userBadgesList = new ArrayList<>();
        String FIND_ALL_USER_BADGES_QUERRY = "SELECT * FROM userbadges";
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL_USER_BADGES_QUERRY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID uid = (UUID) resultSet.getObject("uid");
                    UUID bid = (UUID) resultSet.getObject("bid");
                    Boolean achieved = resultSet.getBoolean("achieved");
                    userBadgesList.add(new UserBadges(userRepository.findOne(uid),badgeRepository.findOne(bid),achieved));
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit(userBadgesList);
        return userBadgesList;
    }

    @Override
    public UserBadges findOne(Pair<UUID> entityID) {
        return null;
    }
}
