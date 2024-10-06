package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EvolutionService {

    private final PokemonRepository pokemonRepository;

    public EvolutionResponses getEvolutionResponses(Pokemon pokemon) {
        EvolutionFactory evolutionFactory = new EvolutionFactory(pokemon);
        Map<String, Integer> depths = evolutionFactory.getDepths();
        List<Evolution> evolutions = evolutionFactory.getEvolutions();

        List<Pokemon> chainedPokemons = getChainedPokemons(pokemon);

        List<EvolutionResponse> responses = chainedPokemons.stream()
                .map(poke -> EvolutionResponse.from(poke, createEvolution(poke, evolutions), depths.get(poke.getId())))
                .toList();

        return new EvolutionResponses(depths.get(pokemon.getId()), responses);
    }

    private Evolution createEvolution(Pokemon pokemon, List<Evolution> evolutions) {
        Optional<Evolution> any = evolutions.stream()
                .filter(evolution -> evolution.getTo().equals(pokemon.getId()))
                .findAny();
        if (any.isPresent()) {
            return any.get();
        }
        return evolutions.stream()
                .filter(evolution -> evolution.getFrom().equals(pokemon.getId()))
                .findAny()
                .orElseThrow();
    }


    private List<Pokemon> getChainedPokemons(Pokemon pokemon) {
        return pokemon.getEvolutions().stream()
                .flatMap(evolution -> Stream.of(evolution.getFrom(), evolution.getTo()))
                .distinct()
                .map(pokemonRepository::findById)
                .filter(Optional::isPresent) // TODO: data is inconsistent, delete filter
                .map(Optional::get)
                .toList();
    }
}
