package com.pokerogue.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.data.pattern.DataPattern;
import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.move.data.MoveFlag;
import com.pokerogue.helper.move.data.MoveTarget;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MoveDataTest extends MongoRepositoryTest {

    private final List<Move> moves;
    private final List<String> pokemonIds;

    @Autowired
    public MoveDataTest(MoveRepository moveRepository, PokemonRepository pokemonRepository) {
        this.moves = moveRepository.findAll();

        List<Pokemon> pokemons = pokemonRepository.findAll();
        this.pokemonIds = pokemons.stream()
                .map(Pokemon::getId)
                .toList();
    }

    @Test
    @DisplayName("Move의 아이디는 영어 소문자와 단일 _로만 이루어져 있다.")
    void id_validateMoveIds() {
        List<String> notMatchMoveIds = moves.stream()
                .map(Move::getId)
                .filter(DataPattern.MOVE_ID_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchMoveIds).isEmpty();
    }

    @Test
    @DisplayName("Move의 name은 영어와 특수문자 그리고 공백으로 이루어져있다.")
    void name_validateMoveNames() {
        List<String> notMatchNames = moves.stream()
                .map(Move::getName)
                .filter(DataPattern.NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchNames).isEmpty();
    }

    @Test
    @DisplayName("Move의 koName은 적어도 하나의 한글이 포함되어 있다.")
    void koName_validateMoveKoNames() {
        List<String> notMatchKoNames = moves.stream()
                .map(Move::getKoName)
                .filter(DataPattern.KO_NAME_PATTERN::isNotMatch)
                .toList();

        assertThat(notMatchKoNames).isEmpty();
    }

    @Test
    @DisplayName("Move의 Type은 모두 Type Enum 이다.")
    void type_validateIsTypeEnum() {
        assertThat(moves.stream()
                .allMatch(move -> move.getType().getDeclaringClass().equals(Type.class)))
                .isTrue();
    }

    @Test
    @DisplayName("Move의 MoveTarget은 모두 Move Target Enum 이다.")
    void target_validateIsTargetEnum() {
        assertThat(moves.stream()
                .allMatch(move -> move.getMoveTarget()
                        .getDeclaringClass()
                        .equals(MoveTarget.class)))
                .isTrue();
    }

    @Test
    @DisplayName("Move의 MoveCategory는 모두 Move Category Enum 이다.")
    void moveCategory_validateIsMoveCategoryEnum() {
        assertThat(moves.stream()
                .allMatch(move -> move.getMoveCategory()
                        .getDeclaringClass()
                        .equals(MoveCategory.class)))
                .isTrue();
    }

    @Test
    @DisplayName("Move의 MoveFlag는 모두 Move Flag Enumd이다")
    void moveFlag_validateIsMoveFlagEnum() {
        assertThat(moves.stream()
                .flatMap(move -> move.getFlags().stream())
        )
                .allMatch(flag -> flag.getDeclaringClass()
                        .equals(MoveFlag.class));
    }

    @Test
    @DisplayName("Move의 Power는 -1이거나 양수의 숫자이다.")
    void power_validateIsMinusOne_OrPositiveDigit() {
        List<Integer> notMatchPowers = moves.stream()
                .map(Move::getPower)
                .filter(this::isNotAvailableDigit)
                .toList();

        assertThat(notMatchPowers).isEmpty();
    }

    @Test
    @DisplayName("Move의 Accuracy는 -1이거나 양수의 숫자이다.")
    void accuracy_validateIsMinusOne_OrPositiveDigit() {
        List<Integer> notMatchAccuracy = moves.stream()
                .map(Move::getAccuracy)
                .filter(this::isNotAvailableDigit)
                .toList();

        assertThat(notMatchAccuracy).isEmpty();
    }

    @Test
    @DisplayName("Move의 PowerPoint는 -1이거나 양수의 숫자이다.")
    void powerPoint_validateIsMinusOne_OrPositiveDigit() {
        List<Integer> notMatchPowerPoint = moves.stream()
                .map(Move::getPowerPoint)
                .filter(this::isNotAvailableDigit)
                .toList();

        assertThat(notMatchPowerPoint).isEmpty();
    }

    @Test
    @DisplayName("Move의 effectChance는 -1이거나 양수의 숫자이다.")
    void effectChance_validateIsMinusOne_OrPositiveDigit() {
        List<Integer> notMatchEffectChance = moves.stream()
                .map(Move::getEffectChance)
                .filter(this::isNotAvailableDigit)
                .toList();

        assertThat(notMatchEffectChance).isEmpty();
    }

    @Test
    @DisplayName("Move의 effect는 최소 한자의 한글이거나 Dummy Data 라는 이름으로 이루어져 있다.")
    void effect_validateIsKorean() {
        List<String> notMatchEffects = moves.stream()
                .map(Move::getEffect)
                .filter(DataPattern.KO_NAME_PATTERN::isNotMatch)
                .filter(koName -> !koName.equals("Dummy Data"))
                .toList();

        assertThat(notMatchEffects).isEmpty();
    }

    @Test
    @DisplayName("Move의 PokemonId 들은 Pokemon Collection 에 존재한다.")
    void pokemonIds_validateInPokemonCollection() {
        List<String> movePokemonIds = moves.stream()
                .map(Move::getPokemonIds)
                .flatMap(List::stream)
                .toList();

        List<String> notMatchIds = movePokemonIds.stream()
                .filter(pokemonId -> !pokemonIds.contains(pokemonId))
                .toList();

        assertThat(notMatchIds).isEmpty();
    }

    @Test
    @DisplayName("Move의 PokemonId 들은 중복되지 않는다.")
    void pokemonIds_NotDuplicated() {
        List<String> duplicatedPokemonMoveIds = moves.stream()
                .filter(move -> isDuplicated(move.getPokemonIds()))
                .map(Move::getId)
                .toList();

        assertThat(duplicatedPokemonMoveIds).isEmpty();
    }

    private boolean isDuplicated(List<String> pokemonIds) {
        return pokemonIds.size() != new HashSet<>(pokemonIds).size();
    }

    private boolean isNotAvailableDigit(int digit) {
        return digit != -1 && digit <= 0;
    }
}
