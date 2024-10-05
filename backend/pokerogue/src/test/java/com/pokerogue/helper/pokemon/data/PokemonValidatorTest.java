package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PokemonValidatorTest {


    @DisplayName("예상하는 포켓몬 개수와 일치하지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 1445, 1447, 20241006})
    void pokemonSize(int pokemonSize) {
        Assertions.assertThatThrownBy(() -> PokemonValidator.validatePokemonSize(pokemonSize))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessageContaining(ErrorMessage.POKEMON_SIZE_MISMATCH.getMessage());
    }
}
