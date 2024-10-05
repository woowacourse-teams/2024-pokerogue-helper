package com.pokerogue.environment.repository;


import com.pokerogue.environment.data.PokemonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableConfigurationProperties(PokemonProperties.class)
public abstract class RepositoryTest {
}
