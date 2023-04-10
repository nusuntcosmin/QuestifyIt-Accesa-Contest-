package com.example.questifyit.domain;

import java.sql.Timestamp;
import java.util.UUID;

public class Quest implements Entity<UUID> {

    private UUID questId;
    private String description;
    private String answer;
    private Timestamp date;
    private Boolean solved;
    private User creator;
    private Integer tokens;

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public Timestamp getDate() {
        return date;
    }

    public Boolean getSolved() {
        return solved;
    }

    public User getCreator() {
        return creator;
    }

    public Integer getTokens() {
        return tokens;
    }

    public Quest(UUID questId, String description, String answer, Timestamp date, Boolean solved, User creator, Integer tokens) {
        this.questId = questId;
        this.description = description;
        this.answer = answer;
        this.date = date;
        this.solved = solved;
        this.creator = creator;
        this.tokens = tokens;
    }

    @Override
    public UUID getEntityID() {
        return questId;
    }
}
