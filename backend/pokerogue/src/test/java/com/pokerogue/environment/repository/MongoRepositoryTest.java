package com.pokerogue.environment.repository;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("local")
public abstract class MongoRepositoryTest {
}
