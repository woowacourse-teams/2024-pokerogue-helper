package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("local")
@SpringBootTest
class EvolutionServiceTest {

    @Autowired
    private PokemonRepository pokemonRepository;


    @DisplayName("")
    @Test
    void test() {
//        EvolutionService evolutionService = new EvolutionService();

//        evolutionService.getEvolutionResponses()
//        List<PokemonTest> all = pokemonRepository.findAll();
//
//        PokemonTest pokemon = pokemonRepository.findById("raichu").orElse(null);
//        List<Evolution> rootEvolutions = evolutionService.getRootEvolutions(pokemon.getEvolutions());
//        for (Evolution rootEvolution : rootEvolutions) {
//            System.out.println(rootEvolution.getFrom());
//        }
    }


}
