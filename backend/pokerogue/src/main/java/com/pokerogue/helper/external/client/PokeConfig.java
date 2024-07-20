package com.pokerogue.helper.external.client;

import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PokeConfig {

    @Bean
    public PokeClient pocketClient() {
        RestClient pocket = getBuilder().build();
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(pocket))
                .build().createClient(PokeClient.class);
    }

    @Bean
    public Builder getBuilder() {
        return RestClient.builder()
                .requestFactory(clientHttpRequestFactory())
                .baseUrl("https://pokeapi.co/api/v2");
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(30));

        return ClientHttpRequestFactories.get(JdkClientHttpRequestFactory.class, settings);
    }
}
