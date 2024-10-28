package com.pokerogue.helper.global.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.pokerogue.helper.biome.converter.TierConverter;
import com.pokerogue.helper.move.converter.FlagConverter;
import com.pokerogue.helper.move.converter.MoveCategoryConverter;
import com.pokerogue.helper.move.converter.MoveTargetConverter;
import com.pokerogue.helper.pokemon.converter.EvolutionItemConverter;
import com.pokerogue.helper.type.converter.TypeReadConverter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.pokerogue"})
public class DataMongoDbConfig {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new TypeReadConverter(),
                new EvolutionItemConverter(),
                new MoveCategoryConverter(),
                new MoveTargetConverter(),
                new FlagConverter(),
                new TierConverter()
        ));
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(10)
                        .minSize(10)
                        .maxWaitTime(2, TimeUnit.SECONDS)
                )
                .build();

        return MongoClients.create(settings);
    }
}
