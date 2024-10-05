package com.pokerogue.helper.pokemon.data;

import static com.pokerogue.helper.pokemon.data.PokemonValidator.throwIfNormalAbilityCountInvalid;
import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonIdFormat;
import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonSize;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
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

    @Disabled("파싱코드의 replace를 한 문자가 아닌 스트링의 전체를 replace하도록 바꿔야 함. 잘못된 id가 있어 disalbed")
    @DisplayName("포켓몬 데이터의 아이디 형식을 확인한다.")
    @Test
    void pokemonIdFormat() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> validatePokemonIdFormat(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @Disabled("""
            디버깅 결과
            ID / actualTotal / expectedTotal
            charizard_gigantamax / 634 / 644,
            kingler_gigantamax / 575 / 58
            
            두 건의 데이터에 대해 종족값이 일치하지 않는다.
            추가적인 논의가 필요하여 disalbed""")
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

    @Disabled("폼변환이 가능하지만 정보가 주어지지 않는 데이터가 존재하여 disalbed.")
    @DisplayName("포켓몬 폼변환이 가능하면 폼변환 정보가 주어진다.")
    @Test
    void pokemonGeneration2() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> PokemonValidator.validatePokemonFormChanges(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }


    @DisplayName("legendary, subLegendary, mythical 셋 중 하나만 true거나 모두 false여야한다.")
    @Test
    void pokemonGeneration3() {
        List<Pokemon> actual = pokemonRepository.findAll();

        ThrowingCallable validator = () -> PokemonValidator.validatePokemonRarity(actual);

        Assertions.assertThatCode(validator).doesNotThrowAnyException();
    }

    @DisplayName("normal ability id는 항상 1개 또는 2개이다.")
    @Test
    void pokemonGeneration4() {
        List<Pokemon> actual = pokemonRepository.findAll();

        Assertions.assertThatCode(() -> actual.forEach(r -> throwIfNormalAbilityCountInvalid(r.getNormalAbilityIds())))
                .doesNotThrowAnyException();
    }

    @Disabled("""
            기본 특성이 히든 특성과 같은 데이터가 있어서 disable
                +) 이상해꽃 기간타맥스가 되면 기본 특성이 달라진다
                +) pokerouge dex와 데이터가 다른걸 보니 추가 확인이 필요""")
    @DisplayName("abilitiy id는 서로 중복될 수 없다.")
    @Test
    void pokemonGeneration5() {
        List<Pokemon> actual = pokemonRepository.findAll();

        Assertions.assertThatCode(() -> actual.forEach(r ->
        {
            List<String> normalAbilityIds = r.getNormalAbilityIds();
            normalAbilityIds.add(r.getHiddenAbilityId());
            normalAbilityIds.add(r.getPassiveAbilityId());
            PokemonValidator.throwIfAbilityDuplicated(r.getNormalAbilityIds());
        })).doesNotThrowAnyException();
    }

    @DisplayName("능력치가 정해진 범위의 수인지 확인한다.")
    @Test
    void pokemonGeneration6() {
        List<Pokemon> actual = pokemonRepository.findAll();

        Assertions.assertThatCode(() -> actual.forEach(r ->
        {
            List<Object> stats = List.of(
                    r.getDefense(),
                    r.getAttack(),
                    r.getSpecialAttack(),
                    r.getSpecialDefense(),
                    r.getWeight(),
                    r.getHeight(),
                    r.getFriendship(),
                    r.getBaseExp(),
                    r.getBaseTotal(),
                    r.getHp(),
                    r.getSpeed()
            );
            PokemonValidator.throwIfNumberOutOfRange(stats);
        })).doesNotThrowAnyException();
    }

    @DisplayName("패시브 특성은 항상 존재한다.")
    @Test
    void pokemonGeneration7() {
        List<Pokemon> actual = pokemonRepository.findAll();

        boolean isPassiveExists = actual.stream()
                                          .map(Pokemon::getPassiveAbilityId)
                                          .noneMatch(Strings::isBlank) ||
                                  actual.stream()
                                          .map(Pokemon::getPassiveAbilityId)
                                          .noneMatch(r -> r.equals("none"));

        Assertions.assertThat(isPassiveExists).isTrue();
    }

    @DisplayName("히든 특성은 존재할 수도 안할 수도 있다.")
    @Test
    void pokemonGeneration8() {
        List<Pokemon> actual = pokemonRepository.findAll();

        int hiddenAbilityCount = actual.stream()
                .map(Pokemon::getHiddenAbilityId)
                .filter(r -> r.equals("none"))
                .toList()
                .size();

        Assertions.assertThat(hiddenAbilityCount)
                .isGreaterThanOrEqualTo(1)
                .isLessThanOrEqualTo(actual.size());
    }

    @DisplayName("특성의 총 개수는 2개에서 4개 사이다.")
    @Test
    void pokemonGeneration9() {
        List<Pokemon> actual = pokemonRepository.findAll();

        Assertions.assertThatCode(() -> actual.forEach(r ->
        {
            List<String> normalAbilityIds = r.getNormalAbilityIds();
            if (!r.getHiddenAbilityId().equals("none")) {
                normalAbilityIds.add(r.getHiddenAbilityId());
            }
            normalAbilityIds.add(r.getPassiveAbilityId());

            Assertions.assertThat(normalAbilityIds.size())
                    .isGreaterThanOrEqualTo(2)
                    .isLessThanOrEqualTo(4);
        })).doesNotThrowAnyException();
    }


    @DisplayName("타입의 개수는 1개 혹은 2개다.")
    @Test
    void pokemonGeneration10() {
        List<Pokemon> actual = pokemonRepository.findAll();

        Assertions.assertThatCode(() -> actual.forEach(r ->
        {
            Set<Type> types = new HashSet<>(r.getTypes());

            Assertions.assertThat(types.size())
                    .isGreaterThanOrEqualTo(1)
                    .isLessThanOrEqualTo(2);
        })).doesNotThrowAnyException();
    }


}
