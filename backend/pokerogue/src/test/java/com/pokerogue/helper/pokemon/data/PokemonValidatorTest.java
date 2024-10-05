package com.pokerogue.helper.pokemon.data;

import static com.pokerogue.helper.pokemon.data.FakePokemon.createNewPokemon;
import static com.pokerogue.helper.pokemon.data.FakePokemon.setField;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
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

    @DisplayName("타입의 개수가 1개 혹은 2개가 아니라면 예외가 발생한다")
    @Test
    void pokemonTypeSize() {
        Pokemon pokemon = createNewPokemon();
        Pokemon pokemon2 = createNewPokemon();

        setField(pokemon, "types", List.of());
        setField(pokemon2, "types", List.of(Type.ICE, Type.FIRE, Type.FAIRY));
        List<Pokemon> pokemons = List.of(pokemon, pokemon2);

        Assertions.assertThatThrownBy(() -> PokemonValidator.validateTypeCount(pokemons))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessageContaining(ErrorMessage.POKEMON_SIZE_MISMATCH.getMessage());
    }

}
