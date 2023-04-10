package com.example.questifyit.domain;

import java.util.UUID;

public class User implements Entity<UUID> {

    private UUID userId;
    private String email;
    private String name;
    private String hashedPassword;
    private String saltPassword;
    private int numberOfTokens;
    private int numberOfQuestsSolved;

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSaltPassword() {
        return saltPassword;
    }

    public int getNumberOfTokens() {
        return numberOfTokens;
    }

    public int getNumberOfQuestsSolved() {
        return numberOfQuestsSolved;
    }

    public User(UUID userId, String email, String name, String hashedPassword, String saltPassword, int numberOfTokens, int numberOfQuestsSolved) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.saltPassword = saltPassword;
        this.numberOfTokens = numberOfTokens;
        this.numberOfQuestsSolved = numberOfQuestsSolved;
    }

    @Override
    public UUID getEntityID() {
        return userId;
    }


}
