package com.pokerogue.helper.global.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.TransportSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        String uri = mongoUri; // MongoDB URI를 여기에 입력
        ConnectionString connectionString = new ConnectionString(uri);
        // TransportSettings를 사용하여 Netty 설정 적용
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .transportSettings(TransportSettings.nettyBuilder()
                        .allocator(io.netty.buffer.UnpooledByteBufAllocator.DEFAULT)
                        .build())
                .build();

        return MongoClients.create(settings);
    }
}

