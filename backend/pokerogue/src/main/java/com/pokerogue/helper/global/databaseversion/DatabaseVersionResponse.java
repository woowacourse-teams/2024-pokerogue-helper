package com.pokerogue.helper.global.databaseversion;

import lombok.Getter;

@Getter
public class DatabaseVersionResponse {

    private int currentVersion;

    private DatabaseVersionResponse() {
    }

    public DatabaseVersionResponse(DatabaseVersion databaseVersion) {
        this.currentVersion = databaseVersion.getVersion();
    }
}
