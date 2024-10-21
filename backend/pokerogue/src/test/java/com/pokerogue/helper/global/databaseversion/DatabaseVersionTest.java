package com.pokerogue.helper.global.databaseversion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "database.version=1")
class DatabaseVersionTest {

    @Autowired
    private DatabaseVersion databaseVersion;

    @Test
    void getVersion() {
        int version = databaseVersion.getVersion();
        assertThat(version).isEqualTo(1);
    }
}
