package com.pokerogue.helper.ability.service;

import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability.dto.AbilityPokemonResponse;
import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.dto.AbilityTypeResponse;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.global.config.LanguageSetter;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final PokemonRepository pokemonRepository;

    public List<AbilityResponse> findAbilities() {
        return abilityRepository.findAll().stream()
                .filter(Ability::isPresent)
                .filter(ability -> ability.hasSameLanguage(LanguageSetter.getLanguage()))
                .map(AbilityResponse::from)
                .toList();
    }

    public AbilityDetailResponse findAbilityDetails(String id) {
        Ability ability = abilityRepository.findByIndexAndLanguage(id, LanguageSetter.getLanguage())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        List<String> abilityPokemonIds = ability.getPokemonIds();
        List<Pokemon> pokemons = abilityPokemonIds.stream()
                .map(pokemonId -> {
                    System.out.println(pokemonId + " " + LanguageSetter.getLanguage());
                    return pokemonRepository.findByIndexAndLanguage(pokemonId, LanguageSetter.getLanguage());
                })
                .map(Optional::get)
                .toList();
        validateExistAllPokemonId(abilityPokemonIds, pokemons);
        List<AbilityPokemonResponse> abilityPokemonResponses = pokemons.stream()
                .map(pokemon -> AbilityPokemonResponse.of(
                        pokemon,
                        ImageUrl.getPokemonImage(pokemon.getImageId()),
                        getAbilityTypeResponses(pokemon.getTypes())
                ))
                .toList();

        return AbilityDetailResponse.of(ability, abilityPokemonResponses);
    }

    private static void validateExistAllPokemonId(
            List<String> abilityPokemonIds,
            List<Pokemon> abilityPokemonResponses
    ) {
        System.out.println(abilityPokemonIds.size() + " " + abilityPokemonResponses.size());
        if (abilityPokemonIds.size() != abilityPokemonResponses.size()) {
            throw new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND);
        }
    }

    private List<AbilityTypeResponse> getAbilityTypeResponses(List<Type> types) {
        return types.stream()
                .map(AbilityTypeResponse::from)
                .toList();
    }
}
