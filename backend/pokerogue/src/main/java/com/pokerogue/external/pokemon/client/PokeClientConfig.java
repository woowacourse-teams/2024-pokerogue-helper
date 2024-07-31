package com.pokerogue.external.pokemon.client;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class PokeClientConfig {

    private static final String BASE_URL = "https://pokeapi.co/api/v2";
    private static final int CONNECT_TIME_OUT_DURATION = 3;
    private static final int READ_TIME_OUT_DURATION = 30;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                .baseUrl(BASE_URL)
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(CONNECT_TIME_OUT_DURATION))
                .withReadTimeout(Duration.ofSeconds(READ_TIME_OUT_DURATION));

        return ClientHttpRequestFactories.get(JdkClientHttpRequestFactory.class, settings);
    }
}
