package com.example.questifyit.repository.database;

import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import com.example.questifyit.repository.database.jdbc_utils.JdbcUtils;
import com.example.questifyit.repository.interfaces.IQuestRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class QuestDbRepository implements IQuestRepository {

    private final JdbcUtils jdbcUtils;

    private IUserRepository userRepository;

    private final Logger log = LogManager.getLogger();
    public QuestDbRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void update(UUID entityID, Quest newEntity) {
        log.traceEntry("Updating quest entity with id {} new quest status : is solved ->  {}, username {}",entityID,newEntity.getSolved());
        Connection con = jdbcUtils.getConnection();
        String UPDATE_QUEST_QUERRY = "UPDATE quest SET solved = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(UPDATE_QUEST_QUERRY)){
            preparedStatement.setBoolean(1,newEntity.getSolved());
            preparedStatement.setObject(2,newEntity.getEntityID());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }
    @Override
    public void add(Quest entityToAdd) {
        log.traceEntry("Saving Quest entity {}",entityToAdd);
        Connection con = jdbcUtils.getConnection();
        String ADD_QUEST_QUERRY = "INSERT INTO quest VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(ADD_QUEST_QUERRY)){
            preparedStatement.setObject(1,entityToAdd.getEntityID());
            preparedStatement.setString(2,entityToAdd.getDescription());
            preparedStatement.setString(3,entityToAdd.getAnswer());
            preparedStatement.setTimestamp(4,entityToAdd.getDate());
            preparedStatement.setBoolean(5,entityToAdd.getSolved());
            preparedStatement.setObject(6,entityToAdd.getCreator().getEntityID());
            preparedStatement.setInt(7,entityToAdd.getTokens());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }
    @Override
    public Iterable<Quest> findAll() {
        log.traceEntry("Selecting all the quests");
        Connection con = jdbcUtils.getConnection();
        List<Quest> questsList = new ArrayList<>();
        String FIND_ALL_QUESTS_QUERRY = "SELECT * FROM user";
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL_QUESTS_QUERRY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID id = (UUID) resultSet.getObject("id");
                    String description = resultSet.getString("description");
                    String answer = resultSet.getString("answer");
                    Timestamp date = resultSet.getTimestamp("date");
                    Boolean solved = resultSet.getBoolean("solved");
                    User creator = userRepository.findOne((UUID) resultSet.getObject("uid"));
                    Integer tokens = resultSet.getInt("tokens");

                    questsList.add(new Quest(id,description,answer,date,solved,creator,tokens));
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit(questsList);
        return questsList;
    }
    @Override
    public Quest findOne(UUID entityID) {
        log.traceEntry("Selecting a quest with id {} ", entityID);
        Connection con = jdbcUtils.getConnection();
        Quest foundQuest = null;
        String FIND_ONE_QUEST_QUERRY = "SELECT * FROM quest WHERE id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ONE_QUEST_QUERRY)) {
            preparedStatement.setObject(1, entityID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID id = (UUID) resultSet.getObject("id");
                    String description = resultSet.getString("description");
                    String answer = resultSet.getString("answer");
                    Timestamp date = resultSet.getTimestamp("date");
                    Boolean solved = resultSet.getBoolean("solved");
                    User creator = userRepository.findOne((UUID) resultSet.getObject("uid"));
                    Integer tokens = resultSet.getInt("tokens");

                    foundQuest = new Quest(id,description,answer,date,solved,creator,tokens);
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit(foundQuest);
        return foundQuest;
    }
}
