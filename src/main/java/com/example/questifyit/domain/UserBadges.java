package com.example.questifyit.domain;

import com.example.questifyit.utils.Pair;

import java.util.UUID;

public class UserBadges implements Entity<Pair<UUID>> {

    private User user;
    private Badge badge;
    private Boolean isAchieved;

    public Boolean getAchieved() {
        return isAchieved;
    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
    }

    public User getUser() {
        return user;
    }

    public Badge getBadge() {
        return badge;
    }

    private Pair<UUID> entityId;

    public UserBadges(User user, Badge badge, Boolean isAchieved) {
        this.user = user;
        this.badge = badge;
        this.isAchieved = isAchieved;
        entityId = new Pair<>(user.getEntityID(),badge.getBadgeId());
    }

    @Override
    public Pair<UUID> getEntityID() {
        return entityId;
    }
}
