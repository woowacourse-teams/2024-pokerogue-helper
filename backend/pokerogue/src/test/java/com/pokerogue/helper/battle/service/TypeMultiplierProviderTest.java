package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TypeMultiplierProviderTest extends ServiceTest {

    @Autowired
    private TypeMultiplierProvider typeMultiplierProvider;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    @DisplayName("타입 상성에 따른 배틀 결과 배수를 구한다.")
    void getByTypeMatching() {
        Pokemon rivalPokemon = pokemonRepository.findById("squirtle").get();
        List<Type> rivalPokemonTypes = rivalPokemon.getTypes()
                .stream()
                .map(String::toUpperCase) // Todo
                .map(Type::valueOf)
                .toList();
        Type attackMoveType = Type.FIRE;

        List<BattleMultiplier> multipliers = typeMultiplierProvider.getAllByTypeMatchings(attackMoveType,
                rivalPokemonTypes);

        assertThat(multipliers).contains(BattleMultiplier.valueOf(2));
    }

    @Test
    @DisplayName("같은 타입 공격 보너스 배수를 구한다.")
    void getBySameTypeAttackBonus() {
        Pokemon rivalPokemon = pokemonRepository.findById("squirtle").get();
        Type attackMoveType = Type.WATER;

        BattleMultiplier multiplier = typeMultiplierProvider.getBySameTypeAttackBonus(attackMoveType, rivalPokemon);

        assertThat(multiplier).isEqualTo(BattleMultiplier.STRONG_MULTIPLIER);
    }

    @Test
    @DisplayName("강한 바람이 불 때 비행타입 라이벌 포켓몬의 약점을 가려주는 배수를 구한다.")
    void getByStrongWind() {
        Pokemon rivalPokemon = pokemonRepository.findById("pidgey").get();
        List<Type> rivalPokemonTypes = rivalPokemon.getTypes()
                .stream()
                .map(String::toUpperCase) // Todo
                .map(Type::valueOf)
                .toList();
        Type attackMoveType = Type.ELECTRIC;

        BattleMultiplier multiplier = typeMultiplierProvider.getByStrongWind(attackMoveType, rivalPokemonTypes);

        assertThat(multiplier).isEqualTo(BattleMultiplier.WEAK_MULTIPLIER);
    }
}
