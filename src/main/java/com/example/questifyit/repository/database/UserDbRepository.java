package com.example.questifyit.repository.database;

import com.example.questifyit.domain.User;
import com.example.questifyit.repository.database.jdbc_utils.JdbcUtils;
import com.example.questifyit.repository.interfaces.IUserRepository;
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

public class UserDbRepository implements IUserRepository {


    private final JdbcUtils jdbcUtils;

    public UserDbRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    private final static Logger log = LogManager.getLogger();
    @Override
    public void update(UUID entityID, User newEntity) {
        log.traceEntry("Updating user entity with id {} new user email {}, username {}",entityID,newEntity.getEmail(),newEntity.getName());
        Connection con = jdbcUtils.getConnection();
        String UPDATE_USER_QUERRY = "UPDATE \"user\" SET name = ? , email = ? , hashedpassword = ? , salt = ?, tokens = ? , quests = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_QUERRY)){
            preparedStatement.setObject(7,newEntity.getEntityID());
            preparedStatement.setString(1,newEntity.getName());
            preparedStatement.setString(2,newEntity.getEmail());
            preparedStatement.setString(3,newEntity.getHashedPassword());
            preparedStatement.setString(4,newEntity.getSaltPassword());
            preparedStatement.setInt(5,newEntity.getNumberOfTokens());
            preparedStatement.setInt(6,newEntity.getNumberOfQuestsSolved());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }

    @Override
    public void add(User entityToAdd) {
        log.traceEntry("Saving User entity {}",entityToAdd);
        Connection con = jdbcUtils.getConnection();
        String ADD_USER_QUERRY = "INSERT INTO \"user\" VALUES ( ? , ? , ? , ? , ? , ? , ? ) ";
        try(PreparedStatement preparedStatement = con.prepareStatement(ADD_USER_QUERRY)){
            preparedStatement.setObject(1,entityToAdd.getEntityID());
            preparedStatement.setString(2,entityToAdd.getName());
            preparedStatement.setString(3,entityToAdd.getEmail());
            preparedStatement.setString(4,entityToAdd.getHashedPassword());
            preparedStatement.setString(5,entityToAdd.getSaltPassword());
            preparedStatement.setInt(6,entityToAdd.getNumberOfTokens());
            preparedStatement.setInt(7,entityToAdd.getNumberOfQuestsSolved());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit();
    }

    @Override
    public Iterable<User> findAll() {
        log.traceEntry("Selecting all the users ");
        Connection con = jdbcUtils.getConnection();
        List<User> userList = new ArrayList<>();
        String FIND_ALL_USER_QUERRY = "SELECT * FROM \"user\"";
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL_USER_QUERRY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID id = (UUID) resultSet.getObject("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String hashedpassword = resultSet.getString("hashedpassword");
                    String salt = resultSet.getString("salt");
                    int tokens = resultSet.getInt("tokens");
                    int quests = resultSet.getInt("quests");

                    userList.add(new User(id,email,name,hashedpassword,salt,tokens,quests));
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
            System.out.println("Error find all users");
        }
        log.traceExit(userList);
        return userList;
    }

    @Override
    public User findOne(UUID entityID) {
        log.traceEntry("Selecting a user with id {} ", entityID);
        Connection con = jdbcUtils.getConnection();
        User foundUser = null;
        String FIND_ONE_USER_QUERRY = "SELECT * FROM \"user\" WHERE id = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ONE_USER_QUERRY)) {
            preparedStatement.setObject(1, entityID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    UUID id = (UUID) resultSet.getObject("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String hashedpassword = resultSet.getString("hashedpassword");
                    String salt = resultSet.getString("salt");
                    int tokens = resultSet.getInt("tokens");
                    int quests = resultSet.getInt("quests");

                    foundUser = new User(id,email,name,hashedpassword,salt,tokens,quests);
                }
            }
        }catch (SQLException sqlException){
            log.error(sqlException);
        }
        log.traceExit(foundUser);
        return foundUser;
    }
}
