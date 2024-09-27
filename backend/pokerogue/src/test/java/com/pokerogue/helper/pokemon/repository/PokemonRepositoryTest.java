package com.pokerogue.helper.pokemon.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.EvolutionItem;
import com.pokerogue.helper.pokemon.data.FormChange;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PokemonRepositoryTest extends MongoRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    @DisplayName("모든 포켓몬 정보가 정상적으로 매핑되는지 확인한다.")
    void findAll_AndConvertToPokemon() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        assertAll(
                () -> assertThat(pokemons).hasSize(1446),
                () -> assertThat(pokemons.stream()
                        .flatMap(pokemon -> pokemon.getTypes().stream())
                        .allMatch(type -> type.getDeclaringClass()
                                .equals(Type.class)))
                        .isTrue(),
                () -> assertThat(pokemons.stream()
                        .flatMap(pokemon -> pokemon.getEvolutions().stream()
                                .map(Evolution::getItem)
                                .filter(Objects::nonNull))
                        .allMatch(item -> item.getDeclaringClass()
                                .equals(EvolutionItem.class)))
                        .isTrue(),
                () -> assertThat(pokemons.stream()
                        .flatMap(pokemon -> pokemon.getFormChanges().stream()
                                .map(FormChange::getItem)
                                .filter(Objects::nonNull))
                        .allMatch(item -> item.getDeclaringClass()
                                .equals(EvolutionItem.class)))
                        .isTrue()
        );
    }
}
