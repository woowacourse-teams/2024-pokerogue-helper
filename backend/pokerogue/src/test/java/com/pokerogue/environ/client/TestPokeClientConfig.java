package com.pokerogue.environ.client;

import com.pokerogue.external.pokemon.client.PokeClient;
import com.pokerogue.external.s3.service.S3Service;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestPokeClientConfig {

    @Bean
    public PokeClient pokeClient() {
        return new FakePokeClient();
    }

    @Bean
    public S3Service s3Service() {
        return new FakeS3Service();
    }
}
