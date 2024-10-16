package com.pokerogue.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.data.pattern.DataPattern;
import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.FormChange;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PokemonDataTest extends MongoRepositoryTest {

    private final List<Pokemon> pokemons;
    private final List<String> pokemonIds;
    private final List<String> abilityIds;
    private final List<String> moveIds;
    private final List<String> biomeIds;

    @Autowired
    public PokemonDataTest(
            PokemonRepository pokemonRepository,
            AbilityRepository abilityRepository,
            MoveRepository moveRepository,
            BiomeRepository biomeRepository
    ) {
        this.pokemons = pokemonRepository.findAll();

        List<Pokemon> pokemons = pokemonRepository.findAll();
        this.pokemonIds = pokemons.stream()
                .map(Pokemon::getId)
                .toList();

        List<Ability> abilities = abilityRepository.findAll();
        abilityIds = abilities.stream()
                .map(Ability::getId)
                .toList();

        List<Move> moves = moveRepository.findAll();
        moveIds = moves.stream()
                .map(Move::getId)
                .toList();

        List<Biome> biomes = biomeRepository.findAll();
        biomeIds = biomes.stream()
                .map(Biome::getId)
                .toList();
    }

    @Test
    @DisplayName("pokemon의 id가 숫자, 영어 소문자 혹은 _로 이루어져있다.")
    void id_validatePokemonIds() {
        List<String> notMatchPokemonIds = pokemonIds.stream()
                .filter(DataPattern.ID_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchPokemonIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 image id가 숫자, 영어 소문자 혹은 _로 이루어져있다.")
    void imageId_validateImageIds() {
        List<String> notMatchingImageIds = pokemons.stream()
                .map(Pokemon::getImageId)
                .filter(DataPattern.ID_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchingImageIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 pokeDexNumber는 양수의 숫자여야 한다.")
    void pokedexNumber_validateIsDigit() {
        List<Integer> notMatchingPokeDexNumbers = pokemons.stream()
                .map(Pokemon::getPokedexNumber)
                .filter(this::isNotPositiveDigit)
                .toList();

        assertThat(notMatchingPokeDexNumbers).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 name가 영어, 특수문자, 공백으로 이루어져있다.")
    void name_validateNames() {
        List<String> notMatchingNames = pokemons.stream()
                .map(Pokemon::getName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchingNames).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 koName이 최소 한 자의 한글로 이루어져 있다.")
    void koName_validateKoName() {
        List<String> notMatchingKoNames = pokemons.stream()
                .map(Pokemon::getKoName)
                .filter(DataPattern.KO_NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchingKoNames).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 speciesName이 영어, 특수문자, 공백으로 이루어져있다.")
    void speciesName_validateSpeciesNames() {
        List<String> notMatchingSpeciesNames = pokemons.stream()
                .map(Pokemon::getSpeciesName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchingSpeciesNames).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 formName이  영어, 특수문자, 숫자, 공백으로 이루어져있다.")
    void formName_validateFormName() {
        List<String> notMatchingFormName = pokemons.stream()
                .map(Pokemon::getFormName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .filter(formName -> !formName.isEmpty())
                .toList();

        assertThat(notMatchingFormName).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 baseExp는 양수로 이루어져 있다.")
    void baseExp_validateIsPositiveNumber() {
        List<Integer> notMatchingBaseExps = pokemons.stream()
                .map(Pokemon::getBaseExp)
                .filter(this::isNotPositiveDigit)
                .toList();

        assertThat(notMatchingBaseExps).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 friendship은 0 이상의 숫자로 이루어져 있다.")
    void friendShip_validateOverZero() {
        List<Integer> notMatchFriendship = pokemons.stream()
                .map(Pokemon::getFriendship)
                .filter(this::isNotZeroOrPositiveDigit)
                .toList();

        assertThat(notMatchFriendship).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 Type은 전부 Enum Type 이다.")
    void type_validateEnumType() {
        assertThat(pokemons.stream()
                .flatMap(pokemon -> pokemon.getTypes().stream()))
                .allMatch(type -> type.getDeclaringClass()
                        .equals(Type.class));
    }

    @Test
    @DisplayName("pokemon의 normalAbilityId 들은 전부 Ability에 존재한다.")
    void abilityId_validateAllInPokemon() {
        List<String> normalAbilityIds = pokemons.stream()
                .map(Pokemon::getNormalAbilityIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchIds = normalAbilityIds.stream()
                .filter(abilityId -> !abilityIds.contains(abilityId))
                .toList();
        assertThat(notMatchIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 normalAbilityId 들은 중복되지 않는다")
    void abilityId_validateIsNotDuplicate() {
        List<String> duplicatedAbilityPokemonIds = pokemons.stream()
                .filter(pokemon -> isDuplicated(pokemon.getNormalAbilityIds()))
                .map(Pokemon::getId)
                .toList();

        assertThat(duplicatedAbilityPokemonIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 hiddenAbilityId 들은 전부 Ability에 존재한다.")
    void hiddenAbilityId_validateAllInPokemon() {
        List<String> hiddenAbilityIds = pokemons.stream()
                .map(Pokemon::getHiddenAbilityId)
                .toList();

        List<String> notMatchIds = hiddenAbilityIds.stream()
                .filter(abilityId -> !abilityIds.contains(abilityId))
                .filter(abilityId -> !abilityId.isEmpty())
                .toList();
        assertThat(notMatchIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 passiveAbilityId 들은 전부 Ability에 존재한다.")
    void passiveAbilityId_validateAllInPokemon() {
        List<String> passiveAbilityIds = pokemons.stream()
                .map(Pokemon::getPassiveAbilityId)
                .toList();

        List<String> notMatchIds = passiveAbilityIds.stream()
                .filter(abilityId -> !abilityIds.contains(abilityId))
                .toList();
        assertThat(notMatchIds).isEmpty();
    }


    @Test
    @DisplayName("pokemon의 모든 Evolution의 from to 는 Pokemon에 존재한다 ")
    void evolutionFromTo_validateAllInPokemonIds() {
        List<Evolution> evolutions = pokemons.stream()
                .map(Pokemon::getEvolutions)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchEvolutionFromIds = evolutions.stream()
                .map(Evolution::getFrom)
                .filter(fromId -> !pokemonIds.contains(fromId))
                .toList();
        List<String> notMatchEvolutionToIds = evolutions.stream()
                .map(Evolution::getTo)
                .filter(toId -> !pokemonIds.contains(toId))
                .toList();

        assertAll(
                () -> assertThat(notMatchEvolutionFromIds).isEmpty(),
                () -> assertThat(notMatchEvolutionToIds).isEmpty()
        );
    }

    @Test
    @DisplayName("pokemon의 모든 FormChange는 Pokemon에 존재한다.")
    void formChangeIds_validateAllInPokemonIds() {
        List<FormChange> formChanges = pokemons.stream()
                .map(Pokemon::getFormChanges)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchFormChangePreviousIds = formChanges.stream()
                .map(formChange -> makeFormChangeIdToPokemonId(formChange.getFrom(), formChange.getPreviousForm()))
                .filter(formChangeId -> !pokemonIds.contains(formChangeId))
                .toList();

        List<String> notMatchFormChangeCurrentIds = formChanges.stream()
                .map(formChange -> makeFormChangeIdToPokemonId(formChange.getFrom(), formChange.getCurrentForm()))
                .filter(formChangeId -> !pokemonIds.contains(formChangeId))
                .toList();

        assertAll(
                () -> assertThat(notMatchFormChangeCurrentIds).isEmpty(),
                () -> assertThat(notMatchFormChangePreviousIds).isEmpty()
        );
    }

    @Test
    @DisplayName("pokemon의 모든 EggMoves는 Move에 존재한다.")
    void eggMoves_validateAllInMove() {
        List<String> eggMoveIds = pokemons.stream()
                .map(Pokemon::getEggMoveIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchMoveIds = eggMoveIds.stream()
                .filter(moveId -> !moveIds.contains(moveId))
                .toList();

        assertThat(notMatchMoveIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 각 EggMoves는 중복되지 않는다.")
    void eggMoves_validateNotDuplicate() {
        List<String> duplicatedEggMovePokemonId = pokemons.stream()
                .filter(pokemon -> isDuplicated(pokemon.getEggMoveIds()))
                .map(Pokemon::getId)
                .toList();

        assertThat(duplicatedEggMovePokemonId).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 모든 levelMoves 는 Move에 존재한다.")
    void levelMoves_validateAllInMove() {
        List<LevelMove> levelMoves = pokemons.stream()
                .map(Pokemon::getLevelMoves)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchLevelMoveIds = levelMoves.stream()
                .map(LevelMove::getMoveId)
                .filter(moveId -> !moveIds.contains(moveId))
                .toList();

        assertThat(notMatchLevelMoveIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 모든 technicalMachineMoveIds는 Move에 존재한다.")
    void technicalMachineMoveIds_validateAllInMove() {
        List<String> technicalMachineMoveIds = pokemons.stream()
                .map(Pokemon::getTechnicalMachineMoveIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchTechnicalMachineIds = technicalMachineMoveIds.stream()
                .filter(technicalMachineMoveId -> !moveIds.contains(technicalMachineMoveId))
                .toList();

        assertThat(notMatchTechnicalMachineIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 각 technicalMachineMoveIds는 중복되지 않는다.")
    void technicalMachineMoveIds_validateNotDuplicate() {
        List<String> duplicateTechnicalMachineMoveIds = pokemons.stream()
                .filter(pokemon -> isDuplicated(pokemon.getTechnicalMachineMoveIds()))
                .map(Pokemon::getId)
                .toList();

        assertThat(duplicateTechnicalMachineMoveIds).isEmpty();
    }

    @Test
    @DisplayName("pokemon의 모든 biomeIds는 Biome에 존재한다.")
    void biomeIds_validateAllInBiome() {
        List<String> pokemonBiomeIds = pokemons.stream()
                .map(Pokemon::getBiomeIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchBiomeIds = pokemonBiomeIds.stream()
                .filter(biomeId -> !biomeIds.contains(biomeId))
                .toList();

        assertThat(notMatchBiomeIds).isEmpty();
    }

    private boolean isDuplicated(List<String> pokemonIds) {
        return pokemonIds.size() != new HashSet<>(pokemonIds).size();
    }

    private String makeFormChangeIdToPokemonId(String pokemonId, String formName) {
        if (formName.isEmpty()) {
            return pokemonId;
        }
        return pokemonId + "_" + formName;
    }

    private boolean isNotPositiveDigit(int input) {
        return input <= 0;
    }

    private boolean isNotZeroOrPositiveDigit(int input) {
        return input < 0;
    }
}
