package com.pokerogue.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.data.pattern.DataPattern;
import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BiomeDataTest extends MongoRepositoryTest {

    private final List<Biome> biomes;
    private final List<String> pokemonIds;

    @Autowired
    public BiomeDataTest(BiomeRepository biomeRepository, PokemonRepository pokemonRepository) {
        this.biomes = biomeRepository.findAll();

        List<Pokemon> pokemons = pokemonRepository.findAll();
        this.pokemonIds = pokemons.stream()
                .map(Pokemon::getId)
                .toList();
    }

    @Test
    @DisplayName("Biome의 아이디는 영어 소문자와 단일 _ 로만 이루어져 있다. ")
    void id_validateBiomeIds() {
        List<String> notMatchBiomeIds = biomes.stream()
                .map(Biome::getId)
                .filter(DataPattern.ID_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchBiomeIds).isEmpty();
    }

    @Test
    @DisplayName("Biome의 name은 영어 문자로 이루어져 있다.")
    void name_compositionWith_English() {
        List<String> notMatchNames = biomes.stream()
                .map(Biome::getName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchNames).isEmpty();
    }

    @Test
    @DisplayName("Biome의 koName은 한글이 포함되어 구성하고 있다.")
    void koName_compositionWith_AtLeastOneKorean() {
        List<String> notMatchNames = biomes.stream()
                .map(Biome::getKoName)
                .filter(this::isNotMatchKoNamePattern)
                .toList();

        assertThat(notMatchNames).isEmpty();
    }

    @Test
    @DisplayName("Biome의 타입들은 전부 Enum Type 안에 들어가있다.")
    void type_isInEnumType() {
        assertThat(biomes.stream()
                .flatMap(biome -> biome.getTypes().stream()))
                .allMatch(type -> type.getDeclaringClass()
                        .equals(Type.class));
    }

    @Test
    @DisplayName("Biome의 Tier 들은 전부 Enum Tier 안에 들어가있다.")
    void tier_isInEnumTier() {
        assertThat(biomes.stream()
                .flatMap(biome -> biome.getNativePokemons().stream()
                        .map(NativePokemon::getTier)
                )
        ).allMatch(tier -> tier.getDeclaringClass().equals(Tier.class));
    }

    @Test
    @DisplayName("Biome의 트레이너들의 타입들은 전부 Enum Type 안에 들어가있다.")
    void trainerType_isInEnumType() {
        List<Type> types = biomes.stream()
                .flatMap(biome -> biome.getTrainers().stream()
                        .map(Trainer::getTypes))
                .flatMap(Collection::stream)
                .toList();

        assertThat(types)
                .allMatch(type -> type.getDeclaringClass().equals(Type.class));
    }

    @Test
    @DisplayName("Biome의 native Pokemon 속 pokemonId들이 전부 존재한다.")
    void nativePokemonId_isInPokemonCollection() {
        List<NativePokemon> nativePokemons = biomes.stream()
                .map(Biome::getNativePokemons)
                .flatMap(List::stream)
                .toList();

        List<String> nativePokemonIds = nativePokemons.stream()
                .map(NativePokemon::getPokemonIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchIds = nativePokemonIds.stream()
                .filter(nativeId -> !pokemonIds.contains(nativeId))
                .toList();

        assertThat(notMatchIds).isEmpty();
    }

    @Test
    @DisplayName("Biome Trainer 의 name 필드가 영어 소문자 혹은 연속되지 않는 _ 로 이루어져 있다.")
    void name_isCompositionWithEnglish() {
        List<Trainer> trainers = biomes.stream()
                .map(Biome::getTrainers)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchTrainerNames = trainers.stream()
                .map(Trainer::getName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchTrainerNames).isEmpty();
    }

    @Test
    @DisplayName("Biome Trainer 의 KoName 필드가 한국어로 이루어져있다.")
    void koName_isCompositionWithKorean() {
        List<Trainer> trainers = biomes.stream()
                .map(Biome::getTrainers)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchTrainerNames = trainers.stream()
                .map(Trainer::getKoName)
                .filter(this::isNotMatchKoNamePattern)
                .toList();

        assertThat(notMatchTrainerNames).isEmpty();
    }

    @Test
    @DisplayName("Biome 의 nativePokemon Id들은 중복되지 않는다.")
    void nativePokemonIds_NotDuplicate() {
        List<List<NativePokemon>> nativePokemons = biomes.stream()
                .map(Biome::getNativePokemons)
                .toList();

        List<NativePokemon> duplicatedNativePokemon = new ArrayList<>();

        nativePokemons.forEach(natives ->
                natives.forEach(nativePokemon -> {
                    if (isDuplicated(nativePokemon.getPokemonIds())) {
                        duplicatedNativePokemon.add(nativePokemon);
                    }
                })
        );

        assertThat(duplicatedNativePokemon).isEmpty();
    }

    @Test
    @DisplayName("Biome의 trainer PokemonId 들은 중복되지 않는다.")
    void trainerPokemonIds_NotDuplicated() {
        List<List<Trainer>> trainers = biomes.stream()
                .map(Biome::getTrainers)
                .toList();

        List<Trainer> duplicatedPokemonTrainer = new ArrayList<>();

        trainers.forEach(trainerList ->
                trainerList.forEach(trainer -> {
                    if (isDuplicated(trainer.getPokemonIds())) {
                        duplicatedPokemonTrainer.add(trainer);
                    }
                })
        );

        assertThat(duplicatedPokemonTrainer).isEmpty();
    }

    private boolean isNotMatchKoNamePattern(String koName) {
        if ("???".equals(koName)) {
            return false;
        }
        return DataPattern.KO_NAME_PATTERN.isNotMatch(koName);
    }

    private boolean isDuplicated(List<String> pokemonIds) {
        return pokemonIds.size() != new HashSet<>(pokemonIds).size();
    }
}
