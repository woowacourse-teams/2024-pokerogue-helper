package com.pokerogue.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.data.pattern.DataPattern;
import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AbilityDataTest extends MongoRepositoryTest {

    private final PokemonRepository pokemonRepository;
    private final List<Ability> abilities;

    @Autowired
    public AbilityDataTest(AbilityRepository abilityRepository, PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
        this.abilities = abilityRepository.findAll();
    }

    @Test
    @DisplayName("Ability의 아이디는 영어 소문자와 단일 _ 로만 이루어져 있다.")
    void id_validateAbilityIds() {
        List<String> notMatchAbilityIds = abilities.stream()
                .map(Ability::getId)
                .filter(DataPattern.ID_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchAbilityIds).isEmpty();
    }

    @Test
    @DisplayName("Ability 속 포켓몬 id들은 전부 존재한다.")
    void pokemonId_validatePokemonIds() {
        List<String> pokemonIds = abilities.stream()
                .map(Ability::getPokemonIds)
                .flatMap(List::stream)
                .toList();
        List<Pokemon> pokemons = pokemonRepository.findAll();
        List<String> ids = pokemons.stream()
                .map(Pokemon::getId)
                .toList();

        List<String> notMatchIds = pokemonIds.stream()
                .filter(pokemonId -> !ids.contains(pokemonId))
                .toList();

        assertThat(notMatchIds).isEmpty();
    }

    @Test
    @DisplayName("모든 능력 설명이 한글이 포함되어 구성하고 있다.")
    void description_compositionWithKorean() {
        List<String> notMatchDescription = abilities.stream()
                .map(Ability::getDescription)
                .filter(DataPattern.DESCRIPTION_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchDescription).isEmpty();
    }

    @Test
    @DisplayName("모든 KoName이 적어도 한 자의 한글과 영어로 이루어져 있다.")
    void koName_compositionWith_AtLeastOneKorean() {
        List<String> notMatchNames = abilities.stream()
                .map(Ability::getKoName)
                .filter(DataPattern.KO_NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchNames).isEmpty();
    }

    @Test
    @DisplayName("모든 Name이 영어로 이루어져 있다.")
    void name_compositionWith_English() {
        List<String> notMatchNames = abilities.stream()
                .map(Ability::getName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchNames).isEmpty();
    }

    @Test
    @DisplayName("Ability의 pokemon Id들은 중복되지 않는다.")
    void pokemonIds_NotDuplicated() {
        List<String> duplicatedPokemonAbilityIds = abilities.stream()
                .filter(ability -> isDuplicated(ability.getPokemonIds()))
                .map(Ability::getId)
                .toList();

        assertThat(duplicatedPokemonAbilityIds).isEmpty();
    }

    private boolean isDuplicated(List<String> pokemonIds) {
        return pokemonIds.size() != new HashSet<>(pokemonIds).size();
    }
}
