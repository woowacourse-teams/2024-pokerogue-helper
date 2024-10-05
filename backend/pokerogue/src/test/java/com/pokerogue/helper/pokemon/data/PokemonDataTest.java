package com.pokerogue.helper.pokemon.data;

import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonIdFormat;
import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonSize;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PokemonDataTest extends RepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @DisplayName("포켓몬 데이터의 개수를 확인한다.")
    @Test
    void pokemonCount() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> validatePokemonSize(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @DisplayName("포켓몬 데이터의 아이디 형식을 확인한다.")
    @Disabled("-로 나오는 아이디가 존재, 파싱코드의 replace를 전체로 replace하도록 바꿔야 함")
    @Test
    void pokemonIdFormat() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> validatePokemonIdFormat(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @DisplayName("포켓몬 데이터의 종족값은 기본 능력치의 합이다.")
    @Disabled("""
            ID actualTotal expectedTotal
            charizard_gigantamax 634 644,
            kingler_gigantamax 575 58
            두 건의 데이터에 대해 종족값이 일치하지 않음""")
    @Test
    void pokemonTotalStats() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable callable = () -> PokemonValidator.validatePokemonTotalState(actual);

        Assertions.assertThatCode(callable).doesNotThrowAnyException();
    }


}
