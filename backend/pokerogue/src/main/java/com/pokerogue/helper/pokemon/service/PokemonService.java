package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public List<PokemonResponse> findPokemons() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        return pokemons.stream()
                .map(this::toPokemonResponse)
                .toList();
    }

    private PokemonResponse toPokemonResponse(Pokemon pokemon) {
        List<PokemonTypeMapping> pokemonTypeMappings = pokemon.getPokemonTypeMappings();

        List<String> typeImages = pokemonTypeMappings.stream()
                .map(PokemonTypeMapping::getCircleTypeImage)
                .toList();

        return PokemonResponse.of(pokemon, typeImages);
    }
}
