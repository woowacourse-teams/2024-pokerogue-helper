package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.global.config.LanguageSetter;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BattleCalculatorTest extends ServiceTest {

    @Autowired
    private BattleCalculator battleCalculator;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    @DisplayName("배틀에서 공격 기술의 정확도를 계산한다.")
    void calculateAccuracy() {
        Move move = moveRepository.findByIndexAndLanguage("ember", LanguageSetter.getLanguage()).get();
        Weather weather = Weather.FOG;

        double accuracy = battleCalculator.calculateAccuracy(move, weather);

        assertThat(accuracy).isEqualTo(90);
    }

    @Test
    @DisplayName("배틀에서 공격 기술의 배수를 계산한다.")
    void calculateBattleResult() {
        Move move = moveRepository.findByIndexAndLanguage("ember", LanguageSetter.getLanguage()).get();
        Weather weather = Weather.SUNNY;
        Pokemon rivalPokemon = pokemonRepository.findByIndexAndLanguage("bulbasaur", LanguageSetter.getLanguage()).get();
        Pokemon myPokemon = pokemonRepository.findByIndexAndLanguage("charmander", LanguageSetter.getLanguage()).get();

        double multiplier = battleCalculator.calculateTotalMultiplier(move, weather, rivalPokemon, myPokemon);

        assertThat(multiplier).isEqualTo(4.5);
    }
}
