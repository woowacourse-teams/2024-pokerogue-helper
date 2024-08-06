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
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        PokemonAbility ability = pokemonAbilityRepository.findByIdWithPokemonAndPokemonTypes(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));

        List<Pokemon> pokemons = ability.getPokemonAbilityMappings().stream()
                .map(PokemonAbilityMapping::getPokemon)
                .distinct()
                .sorted(Comparator.comparingLong(Pokemon::getId))
                .toList();
        List<SameAbilityPokemonResponse> pokemonResponses = toSameAbilityPokemonResponses(pokemons);

        return new PokemonAbilityWithPokemonsResponse(ability.getKoName(), ability.getDescription(), pokemonResponses);
    }

    private List<SameAbilityPokemonResponse> toSameAbilityPokemonResponses(List<Pokemon> pokemons) {
        Map<Pokemon, List<PokemonType>> pokemonTypeMappings = mapTypeWithPokemons(pokemons);

        return pokemons.stream()
                .map(pokemon -> SameAbilityPokemonResponse.from(pokemon,
                        toPokemonTypeResponse(pokemonTypeMappings.get(pokemon))))
                .toList();
    }

    private Map<Pokemon, List<PokemonType>> mapTypeWithPokemons(List<Pokemon> pokemons) {
        return pokemons.stream()
                .collect(Collectors.toMap(
                        pokemon -> pokemon,
                        pokemon -> pokemon.getPokemonTypeMappings().stream()
                                .map(PokemonTypeMapping::getPokemonType)
                                .toList()
                ));
    }

    private List<PokemonTypeResponse> toPokemonTypeResponse(List<PokemonType> pokemonTypes) {
        return pokemonTypes.stream()
                .map(PokemonTypeResponse::from)
                .toList();
    }
}
