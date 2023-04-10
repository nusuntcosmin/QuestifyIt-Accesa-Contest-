package com.example.questifyit.repository.database;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.repository.database.jdbc_utils.JdbcUtils;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class BadgeDbRepository implements IBadgeRepository {


    private JdbcUtils jdbcUtils;

    private final static Logger log = LogManager.getLogger();

    public BadgeDbRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Iterable<Badge> findAll() {
        log.traceEntry("Selecting all the badges from database");

        Connection con = jdbcUtils.getConnection();
        List<Badge> badgesList = new ArrayList<>();

        String SELECT_ALL_BADGES_QUERRY = "SELECT * FROM badge";
        try(PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_BADGES_QUERRY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID badgeId = (UUID) resultSet.getObject("id");
                    String badgeName = resultSet.getString("name");
                    String badgePhotoResourcePath = resultSet.getString("path");

                    badgesList.add(new Badge(badgeId,badgeName,badgePhotoResourcePath));
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
        }

        log.traceExit(badgesList);
        return badgesList;
    }

    @Override
    public Badge findOne(UUID entityID) {
        log.traceEntry("Selecting a badge with id {}", entityID);
        String FIND_ONE_BADGE_QUERRY = "SELECT * FROM badge WHERE id = ?";
        Connection con = jdbcUtils.getConnection();
        Badge foundBadge = null;
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ONE_BADGE_QUERRY)) {
            preparedStatement.setObject(1, entityID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){

                    UUID badgeId = (UUID) resultSet.getObject("id");
                    String badgeName = resultSet.getString("name");
                    String badgePhotoResourcePath = resultSet.getString("path");

                    foundBadge = new Badge(badgeId,badgeName,badgePhotoResourcePath);

                    return foundBadge;

                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);

        }
        log.traceExit(foundBadge);
        return foundBadge;
    }
}
