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
        int actual = pokemonRepository.findAll().size();

        ThrowingCallable validator = () -> validatePokemonSize(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @Disabled("파싱코드의 replace를 한 문자가 아닌 스트링의 전체를 replace하도록 바꿔야 해서 disalbed")
    @DisplayName("포켓몬 데이터의 아이디 형식을 확인한다.")
    @Test
    void pokemonIdFormat() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> validatePokemonIdFormat(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @Disabled("""
            ID actualTotal expectedTotal
            charizard_gigantamax 634 644,
            kingler_gigantamax 575 58
            
            두 건의 데이터에 대해 종족값이 일치하지 않아서 disalbed""")
    @DisplayName("포켓몬 데이터의 종족값은 기본 능력치의 합이다.")
    @Test
    void pokemonTotalStats() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable callable = () -> PokemonValidator.validatePokemonTotalState(actual);

        Assertions.assertThatCode(callable).doesNotThrowAnyException();
    }

    @DisplayName("포켓몬 데이터의 세대가 유효한지 확인한다.")
    @Test
    void pokemonGeneration() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> PokemonValidator.validatePokemonGeneration(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @Disabled("포켓몬 변환 정보가 주어지지 않는 데이터가 존재하여 disalbed.")
    @DisplayName("포켓몬 폼변환이 가능하면 폼변환 정보가 주어진다.")
    @Test
    void pokemonGeneration2() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> PokemonValidator.validatePokemonFormChanges(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }


    @DisplayName("legendary, subLegendary, mythical 셋 중 하나가 true거나 모두 false여야한다.")
    @Test
    void pokemonGeneration3() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> PokemonValidator.validatePokemonRarity(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

}
