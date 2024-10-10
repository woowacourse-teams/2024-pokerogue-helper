package com.pokerogue.helper.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("포켓몬 타입의 개수가 1개 혹은 2개가 아니라면 예외가 발생한다")
    @Test
    void pokemonTypeSize() {
        Pokemon pokemon = new Pokemon();
        Pokemon pokemon2 = new Pokemon();

        pokemon.setTypes(List.of());
        pokemon2.setTypes(List.of(Type.FIRE, Type.ICE, Type.BUG));

        List<Pokemon> pokemons = List.of(pokemon, pokemon2);

        Assertions.assertThatThrownBy(() -> PokemonValidator.validateTypeCount(pokemons))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessageContaining(ErrorMessage.POKEMON_TYPE_COUNT_OUT_OF_RANGE.getMessage());
    }

}
