package com.example.questifyit.domain;

import java.util.Objects;
import java.util.UUID;

public class User implements Entity<UUID> {

    private UUID userId;
    private String email;
    private String name;

    @Override
    public String toString() {
        return name +" (" + email + ") ; Solved quests: " + numberOfQuestsSolved + " ; Tokens earned: " + numberOfTokens;
    }

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

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSaltPassword(String saltPassword) {
        this.saltPassword = saltPassword;
    }

    public void setNumberOfTokens(int numberOfTokens) {
        this.numberOfTokens = numberOfTokens;
    }

    public void setNumberOfQuestsSolved(int numberOfQuestsSolved) {
        this.numberOfQuestsSolved = numberOfQuestsSolved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
