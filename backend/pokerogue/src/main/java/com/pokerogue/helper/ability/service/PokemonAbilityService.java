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
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonAbilityService {

    private final PokemonAbilityRepository pokemonAbilityRepository;

    public List<PokemonAbilityResponse> findAbilities() {
        List<PokemonAbility> pokemonAbilities = pokemonAbilityRepository.findAll();

        return pokemonAbilities.stream()
                .map(PokemonAbilityResponse::from)
                .toList();
    }

    public PokemonAbilityWithPokemonsResponse findAbilityDetails(Long id) {
        PokemonAbility ability = pokemonAbilityRepository.findByIdWithPokemonMappings(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));

        List<SameAbilityPokemonResponse> pokemonResponses = ability.getPokemonAbilityMappings().stream()
                .map(PokemonAbilityMapping::getPokemon)
                .map(pokemon -> SameAbilityPokemonResponse.from(pokemon, toPokemonTypeResponse(pokemon)))
                .toList();

        return new PokemonAbilityWithPokemonsResponse(ability.getKoName(), ability.getDescription(), pokemonResponses);
    }

    private List<PokemonTypeResponse> toPokemonTypeResponse(Pokemon pokemon) {
        return pokemon.getPokemonTypeMappings().stream()
                .map(PokemonTypeMapping::getPokemonType)
                .map(PokemonTypeResponse::from)
                .toList();
    }
}
