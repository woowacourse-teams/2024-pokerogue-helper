package com.pokerogue.helper.global.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.TransportSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String uri = "mongodb://root:example@10.0.100.218:3306/pokerogue?authSource=admin"; // MongoDB URI를 여기에 입력
        ConnectionString connectionString = new ConnectionString(uri);

        // TransportSettings를 사용하여 Netty 설정 적용
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .transportSettings(TransportSettings.nettyBuilder().build())
                .build();

        return MongoClients.create(settings);
    }
}

