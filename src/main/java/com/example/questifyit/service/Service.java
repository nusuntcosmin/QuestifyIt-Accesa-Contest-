package com.example.questifyit.service;

import com.example.questifyit.domain.Badge;
import com.example.questifyit.domain.User;
import com.example.questifyit.domain.UserBadges;
import com.example.questifyit.exceptions.NoMatchingPassword;
import com.example.questifyit.exceptions.NoValidEmail;
import com.example.questifyit.repository.interfaces.IBadgeRepository;
import com.example.questifyit.repository.interfaces.IQuestRepository;
import com.example.questifyit.repository.interfaces.IUserBadgesRepository;
import com.example.questifyit.repository.interfaces.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
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




