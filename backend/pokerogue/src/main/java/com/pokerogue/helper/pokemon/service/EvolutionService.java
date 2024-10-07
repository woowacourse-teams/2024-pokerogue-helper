package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EvolutionService {

    private final PokemonRepository pokemonRepository;

    public EvolutionResponses getEvolutionResponses(Pokemon pokemon) {
        List<Evolution> evolutions = pokemon.getEvolutions();
        List<Pokemon> connectedPokemons = createConnectedPokemons(evolutions);
        EvolutionContext evolutionContext = new EvolutionContext(evolutions);

        List<EvolutionResponse> responses = connectedPokemons.stream()
                .map(pkmn -> createEvolutionResponse(pkmn, evolutionContext))
                .toList();

        return new EvolutionResponses(evolutionContext.getDepthOf(pokemon.getId()), responses);
    }

    private EvolutionResponse createEvolutionResponse(Pokemon pokemon, EvolutionContext evolutionContext) {
        return EvolutionResponse.from(pokemon, evolutionContext.getEvolutionOf(pokemon.getId()),
                evolutionContext.getDepthOf(pokemon.getId()));
    }

    private List<Pokemon> createConnectedPokemons(List<Evolution> evolutions) {
        return evolutions.stream()
                .flatMap(evolution -> Stream.of(evolution.getFrom(), evolution.getTo()))
                .distinct()
                .map(pokemonRepository::findById)
                .filter(Optional::isPresent) // TODO: data is inconsistent, delete filter
                .map(Optional::get)
                .toList();
    }
}
