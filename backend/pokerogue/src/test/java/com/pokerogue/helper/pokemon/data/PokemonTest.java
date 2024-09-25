package com.pokerogue.helper.pokemon.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PokemonTest {

    @ParameterizedTest
    @MethodSource(value = "typeAndExpectedResult")
    @DisplayName("포켓몬이 가진 타입 일치를 확인한다.")
    void hasSameType(Type type, boolean expectedResult) {
        Pokemon bulbasaur = new Pokemon(
                "1",                            // id
                "bulbasaur-image-id",             // imageId
                1,                                // pokedexNumber
                "Bulbasaur",                      // name
                "이상해씨",                         // koName
                "Seed Pokémon",                   // speciesName
                true,                             // canChangeForm
                "Normal",                         // formName
                64,                               // baseExp
                70,                               // friendship
                List.of("grass", "poison"),       // types
                List.of("Overgrow"),          // normalAbilityIds
                "Chlorophyll",                    // hiddenAbilityId
                "None",                           // passiveAbilityId
                1,                                // generation
                false,                            // legendary
                false,                            // subLegendary
                false,                            // mythical
                List.of(),                        // evolutions
                List.of(),                       // formChanges
                318,                              // baseTotal
                45,                               // hp
                49,                               // attack
                49,                               // defense
                65,                               // specialAttack
                65,                               // specialDefense
                45,                               // speed
                0.7,                              // height (in meters)
                6.9,                              // weight (in kilograms)
                List.of("Leech Seed"),        // eggMoveIds
                List.of(),                       // levelMoves
                List.of("TM01", "TM02"),          // technicalMachineMoveIds
                List.of("Forest", "Grassland")   // biomeIds
        );

        boolean result = bulbasaur.hasSameType(type);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> typeAndExpectedResult() {
        return Stream.of(Arguments.of(Type.GRASS, true), Arguments.of(Type.FIRE, false));
    }
}
