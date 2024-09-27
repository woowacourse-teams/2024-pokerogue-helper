package com.pokerogue.environment.repository;

import com.pokerogue.helper.global.config.ConverterConfig;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("local")
@Import(ConverterConfig.class)
public abstract class MongoRepositoryTest {
}
