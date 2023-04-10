package com.example.questifyit.domain;

import java.util.UUID;

public class Badge implements Entity<UUID> {


    private UUID badgeId;

    private String badgeName;

    private String badgePhotoResourcePath;

    public Badge(UUID badgeId, String badgeName, String badgePhotoResourcePath) {
        this.badgeId = badgeId;
        this.badgeName = badgeName;
        this.badgePhotoResourcePath = badgePhotoResourcePath;
    }

    public UUID getBadgeId() {
        return badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public String getBadgePhotoResourcePath() {
        return badgePhotoResourcePath;
    }

    @Override
    public UUID getEntityID() {
        return badgeId;
    }
}
