package com.example.questifyit.service;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.Quest;
import com.example.questifyit.domain.User;
import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.exceptions.NoMatchingPassword;
import com.example.questifyit.exceptions.NoValidEmail;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import com.example.questifyit.repository.interfaces.IQuestRepository;
import com.example.questifyit.repository.interfaces.IUserBadgesRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import com.example.questifyit.utils.data_structures.Pair;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class Service {

    private IUserRepository userRepository;
    private IBadgeRepository badgeRepository;
    private IQuestRepository questRepository;
    private IUserBadgesRepository userBadgesRepository;

    private final Logger log = LogManager.getLogger();

    public Service(IUserRepository userRepository, IBadgeRepository badgeRepository, IQuestRepository questRepository, IUserBadgesRepository userBadgesRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.questRepository = questRepository;
        this.userBadgesRepository = userBadgesRepository;
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User findOneUser(UUID userID){
        return userRepository.findOne(userID);
    }
    public Iterable<Quest> findAllQuests(){
        return questRepository.findAll();
    }

    public void checkQuestAnswer(Quest quest ,User questSolver,String givenAnswer) throws Exception {
        if(givenAnswer.isEmpty())
            throw new Exception("Please complete the answer before submitting !");

        if(!givenAnswer.equals(quest.getAnswer()))
            throw new Exception("Wrong answer !");

        int tokensReward = quest.getTokens();
        questSolver.setNumberOfTokens(questSolver.getNumberOfTokens() + tokensReward);

        questSolver.setNumberOfQuestsSolved(questSolver.getNumberOfQuestsSolved() + 1);
        userRepository.update(questSolver.getEntityID(),questSolver);
        quest.setSolver(questSolver);
        quest.setSolved(true);
        questRepository.update(quest.getEntityID(),quest);


        updateQuestBadges(questSolver);
        updateTokensBadges(questSolver);
        updateTopBadges(questSolver);

    }
    private void updateQuestBadges(User rewardedUser){

        if(rewardedUser.getNumberOfQuestsSolved()>= 1){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("48c9d823-f44a-4b90-8d14-7b9f4fa63ecc"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }
        if(rewardedUser.getNumberOfQuestsSolved()>= 10){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("9173299b-dadd-4c6c-9d4b-5fdc1a9a230d"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }
    }
    private void updateTokensBadges(User rewardedUser){
        if(rewardedUser.getNumberOfTokens() >= 100){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("b7d44af1-9b46-4692-a50f-1d7381d9794f"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }

        if(rewardedUser.getNumberOfTokens() >= 500){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("301acbcb-6187-4549-adbc-6c97a5dd5a21"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }

        if(rewardedUser.getNumberOfTokens() >= 1000){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("3706565d-1964-4245-acfe-3b6dd4a4541f"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }
    }

    private void updateTopBadges(User rewardedUser){
        ArrayList<User> userArrayList = (ArrayList<User>) findAllUsers();
        if(!userArrayList.isEmpty() && userArrayList.get(0).equals(rewardedUser)){
            Pair<UUID> userBadgesID = new Pair<>(rewardedUser.getUserId(),UUID.fromString("d2a6d5d8-3a71-4b38-a2e6-f072d490db07"));
            UserBadges userBadges = userBadgesRepository.findOne(userBadgesID);
            if(userBadges.getAchieved().equals(false)) {
                userBadges.setAchieved(true);
                userBadgesRepository.update(userBadgesID, userBadges);
            }
        }
    }

    public void addQuest(String description, String answer, User creator, String stringNrOftokens) throws Exception {
        if(description.isEmpty() || answer.isEmpty() || stringNrOftokens.isEmpty())
            throw new Exception("Please complete all fields !");

        int tokens;

        try{
            tokens = Integer.parseInt(stringNrOftokens);
        }catch (Exception ex){
            throw new Exception("Invalid number of tokens!");
        }

        if(tokens <= 0)
            throw new Exception("Reward cannot be negative or zero!");
        if(tokens > creator.getNumberOfTokens())
            throw new Exception("Insufficient funds !");

        questRepository.add(new Quest(UUID.randomUUID(),description,answer, new Timestamp(System.currentTimeMillis()), false,creator,tokens,null));
        creator.setNumberOfTokens(creator.getNumberOfTokens() - tokens);
        userRepository.update(creator.getEntityID(),creator);
    }

    public void updateUser(UUID userID, String name, String email, String password, String confirmPassword) throws Exception {
        User userToUpdate = userRepository.findOne(userID);

        if((!password.isEmpty() && confirmPassword.isEmpty()) || (password.isEmpty() && !confirmPassword.isEmpty()))
            throw new NoMatchingPassword("Please complete all passwords fields");

        if(!password.equals(confirmPassword))
            throw new NoMatchingPassword("Passwords do not match");

        if(!password.isEmpty()){
            String salt = generatePasswordSalt();
            userToUpdate.setHashedPassword(hashPassword(password,salt));
        }

        if(!email.isEmpty()){
            checkEmailValidity(email);
            userToUpdate.setEmail(email);
        }

        if(!name.isEmpty()){
            checkNameValidity(name);
            userToUpdate.setName(name);
        }

        userRepository.update(userID,userToUpdate);

    }

    public Iterable<Quest> findAllSolvedQuestsForUser(UUID userID){
        List<Quest> listQuest =  ((ArrayList<Quest>) questRepository.findAll()).
                stream().
                    filter(quest -> {
                        if(quest.getSolved().equals(true))
                            if(quest.getSolver().getUserId().equals(userID))
                                return true;
                        return false;
                    }).
                        toList();

        return listQuest;
    }
    public Iterable<Badge> findAllBadge() {
        return badgeRepository.findAll();
    }

    /*
    //
    //   Hashing and generating salts
    //
    */

    private String generatePasswordSalt() {
        log.traceEntry("Generating a salt for password");
        SecureRandom random = null;
        byte[] salt = new byte[8];

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
        }

        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String rawPassword, String salt) {
        log.traceEntry("Hashing password");

        String usedAlgorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLenght = 160;
        int iterations = 20000;

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), saltBytes, iterations, derivedKeyLenght);

        SecretKeyFactory secretKeyFactory = null;
        byte[] encryptedBytes = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(usedAlgorithm);
            encryptedBytes = secretKeyFactory.generateSecret(spec).getEncoded();

        } catch (Exception e) {
            log.error(e);
        }

        log.traceEntry(Base64.getEncoder().encodeToString(encryptedBytes));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /*
    //
    //   Register, Login, and update password
    //
    */

    private void checkEmailValidity(String givenEmail) throws NoValidEmail {
        if (!givenEmail.matches("^(.+)@(.+)$"))
            throw new NoValidEmail("Email is not valid !");
        userRepository.findAll();

        for (User u : (ArrayList<User>) userRepository.findAll()) {
            if (u.getEmail().equals(givenEmail))
                throw new NoValidEmail("There is already an account having this email !");
        }
    }

    private void checkNameValidity(String name) throws Exception {
        if (!name.matches("^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$"))
            throw new Exception("Name is not valid");
    }

    private void addBadgesForUser(User user){
        badgeRepository.findAll().forEach(
                badge -> {
                    boolean achieved = false;
                    if(badge.getBadgeId().equals(UUID.fromString("12843455-5c56-47ca-8611-2b372f833153")))
                        achieved = true;

                    userBadgesRepository.add(
                            new UserBadges(user,badge,achieved)
                    );
                }
        );
    }
    public void registerUser(String rawPassword, String confirmPassword, String email, String name) throws Exception {
        log.traceEntry("Trying to register user with email {}" ,email);
        if(rawPassword.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || name.isEmpty())
            throw new Exception("Please complete all fields");
        if (!rawPassword.equals(confirmPassword))
            throw new NoMatchingPassword("Passwords do not match");

        checkEmailValidity(email);
        checkNameValidity(name);

        String salt = generatePasswordSalt();
        User userToAdd = new User(UUID.randomUUID(), email, name, hashPassword(rawPassword, salt), salt, 20, 0);
        userRepository.add(userToAdd);
        addBadgesForUser(userToAdd);
    }


    public void checkHashingPasswords(String email, String password) throws Exception {
        User userToLogin = findUserByEmail(email);
        String userPasswordSalt = userToLogin.getSaltPassword();
        String hashedPassword = hashPassword(password, userPasswordSalt);

        if (!hashedPassword.equals(userToLogin.getHashedPassword()))
            throw new NoMatchingPassword("Wrong password");
    }

    public User loginUser(String email, String givenPassword) throws Exception {
        if(givenPassword.isEmpty() ||  email.isEmpty())
            throw new Exception("Please complete all fields");
        checkHashingPasswords(email,givenPassword);
        return findUserByEmail(email);
    }

    private User findUserByEmail(String givenEmail) throws NoValidEmail {
        return ((ArrayList<User>)userRepository.findAll()).
                stream().
                    filter(user -> user.getEmail().equals(givenEmail)).
                        findAny().
                            orElseThrow(() -> new NoValidEmail("We could not find an account with this email"));

    }

    public Iterable<Quest> getQuestsForUser(UUID loggedUserID){
        List<Quest> listQuest =  ((ArrayList<Quest>) questRepository.findAll()).
                stream().
                    filter(quest -> quest.getCreator().getUserId().equals(loggedUserID)).
                        toList();
        return listQuest;
    }
    public Iterable<Quest> getOtherUsersQuests(UUID loggedUserID){
        List<Quest> listQuest =  ((ArrayList<Quest>) questRepository.findAll()).
                stream().
                    filter(quest -> !quest.getCreator().getUserId().equals(loggedUserID)).
                        toList();
        return listQuest;
    }

    /*
    //
    //      Badges
    //
     */

    public Badge findOneBadge(UUID badgeID){
        return badgeRepository.findOne(badgeID);
    }
    public Iterable<UserBadges> getBadgesForUser(UUID userID){
        return ((ArrayList<UserBadges>) userBadgesRepository.
                findAll()).
                    stream().
                        filter(userBadges -> userBadges.getEntityID().getFirst().equals(userID)).
                            toList();
    }
}




