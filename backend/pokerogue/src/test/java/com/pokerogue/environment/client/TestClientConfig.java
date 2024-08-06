package com.pokerogue.environment.client;

import com.pokerogue.external.pokemon.client.PokeClient;
import com.pokerogue.external.s3.client.S3ImageClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestClientConfig {

    @Bean
    public PokeClient pokeClient() {
        return new FakePokeClient();
    }

    @Bean
    public S3ImageClient s3ImageClient() {
        return new FakeS3ImageClient();
    }
}