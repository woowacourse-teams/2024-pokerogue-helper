package com.pokerogue.helper.type.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonTypeMatchingRepositoryTest extends RepositoryTest {

    @Autowired
    private PokemonTypeMatchingRepository pokemonTypeMatchingRepository;

    @Test
    @DisplayName("포켓몬의 모든 타입 매칭 정보를 조회한다.")
    void findAll() {
        List<PokemonTypeMatching> pokemonTypeMatchings = pokemonTypeMatchingRepository.findAll();

        Assertions.assertThat(pokemonTypeMatchings).isNotEmpty();
    }

}
