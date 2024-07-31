package com.pokerogue.external.pokemon.service;

import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.environ.client.FakePokeClient;
import com.pokerogue.helper.environ.service.ServiceTest;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class DataSettingServiceTest extends ServiceTest {
    @Autowired
    private DataSettingService dataSettingService;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

    @Test
    @DisplayName("포켓몬 데이터를 세팅한다.")
    void setData() {
        dataSettingService.setData();

        int savedPokemonCount = pokemonRepository.findAll().size();
        int savedTypeCount = pokemonTypeRepository.findAll().size();
        int savedAbilityCount = pokemonAbilityRepository.findAll().size();
        assertThat(savedPokemonCount).isEqualTo(FakePokeClient.TEST_POKEMON_COUNT);
        assertThat(savedTypeCount).isEqualTo(FakePokeClient.TEST_TYPE_COUNT);
        assertThat(savedAbilityCount).isEqualTo(FakePokeClient.TEST_ABILITY_COUNT);
    }
}
