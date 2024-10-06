package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
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
        EvolutionService evolutionService = new EvolutionService(pokemonRepository);

        List<Pokemon> all = pokemonRepository.findAll();

        for (Pokemon pokemon : all) {
            System.out.println(evolutionService.getEvolutionResponses(pokemon));
        }




//        for (Evolution rootEvolution : rootEvolutions) {
//            System.out.println(rootEvolution.getFrom());
//        }
    }


}
