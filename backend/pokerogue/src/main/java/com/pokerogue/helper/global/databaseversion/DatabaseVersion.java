package com.pokerogue.helper.global.databaseversion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseVersion {

    @Value("${database.version}")
    private String version;

    public int getVersion() {
        return Integer.parseInt(version);
    }
}
