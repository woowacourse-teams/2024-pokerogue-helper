package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.dto.PokedexResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        List<PokemonTypeResponse> pokemonTypeResponses = pokemonTypeMappings.stream()
                .map(PokemonTypeMapping::getPokemonType)
                .map(PokemonTypeResponse::from)
                .toList();

        return PokemonResponse.of(pokemon, pokemonTypeResponses);
    }

    @Transactional(readOnly = true)
    public PokedexResponse findPokedexDetails(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        return toPokedexResponse(pokemon);
    }

    private PokedexResponse toPokedexResponse(Pokemon pokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = pokemon.getPokemonTypeMappings().stream()
                .map(PokemonTypeMapping::getPokemonType)
                .map(PokemonTypeResponse::from)
                .toList();
        List<PokemonAbilityResponse> pokemonAbilityResponses = pokemon.getPokemonAbilityMappings().stream()
                .map(PokemonAbilityMapping::getPokemonAbility)
                .map(PokemonAbilityResponse::from)
                .toList();

        return PokedexResponse.of(pokemon, pokemonTypeResponses, pokemonAbilityResponses);
    }
}
