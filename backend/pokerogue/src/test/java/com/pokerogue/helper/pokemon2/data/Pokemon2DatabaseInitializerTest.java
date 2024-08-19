package com.pokerogue.helper.pokemon2.data;

import static org.junit.jupiter.api.Assertions.*;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.global.config.DatabaseInitializer;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;


class Pokemon2DatabaseInitializerTest {

    @Test
    @DisplayName("포켓몬 정보가 지정된 개수만큼 저장된다")
    void run() {
        Pokemon2Repository pokemon2Repository = new Pokemon2Repository();
        Pokemon2DatabaseInitializer pokemon2DatabaseInitializer = new Pokemon2DatabaseInitializer(
                pokemon2Repository,
                new MoveRepository()
        );

        pokemon2DatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(pokemon2Repository.findAll()).hasSize(1350);
    }
}
