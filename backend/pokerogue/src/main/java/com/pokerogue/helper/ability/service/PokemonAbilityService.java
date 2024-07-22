package com.pokerogue.helper.ability.service;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.dto.PokemonAbilityWithPokemonsResponse;
import com.pokerogue.helper.ability.dto.SameAbilityPokemonResponse;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonAbilityService {

    private final PokemonAbilityRepository pokemonAbilityRepository;
    private final PokemonRepository pokemonRepository;


    public List<PokemonAbilityResponse> findAbilities() {
        List<PokemonAbility> pokemonAbilities = pokemonAbilityRepository.findAll();

        return pokemonAbilities.stream()
                .map(PokemonAbilityResponse::from)
                .toList();
    }

    public PokemonAbilityWithPokemonsResponse findAbilityDetails(Long id) {
        PokemonAbility ability = pokemonAbilityRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.ABILITY_NOT_FOUND));

        List<Pokemon> pokemons = pokemonRepository.findAll().stream()
                .filter(pokemon ->
                        pokemon.getPokemonAbilityMappings()
                                .stream()
                                .map(PokemonAbilityMapping::getPokemonAbilityName)
                                .anyMatch(name -> name.equals(ability.getName()))
                ).toList();

        return new PokemonAbilityWithPokemonsResponse(
                ability.getName(),
                ability.getDescription(),
                pokemons.stream()
                        .map(SameAbilityPokemonResponse::from)
                        .toList()
        );

    }
}
