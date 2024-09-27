package com.pokerogue.helper.ability.service;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability.dto.AbilityPokemonResponse;
import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.dto.AbilityTypeResponse;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final S3Service s3Service;
    private final AbilityRepository abilityRepository;
    private final PokemonRepository pokemonRepository;

    public List<AbilityResponse> findAbilities() {
        return abilityRepository.findAll().stream()
                .filter(Ability::isNotNone)
                .map(AbilityResponse::from)
                .toList();
    }

    public AbilityDetailResponse findAbilityDetails(String id) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        List<String> abilityPokemonIds = ability.getPokemonIds();
        List<AbilityPokemonResponse> abilityPokemonResponses = pokemonRepository.findAllById(abilityPokemonIds).stream()
                .map(pokemon -> new AbilityPokemonResponse(
                        pokemon.getId(),
                        (long) pokemon.getPokedexNumber(),
                        pokemon.getKoName(),
                        s3Service.getPokemonImageFromS3(pokemon.getImageId()),
                        getAbilityTypeResponses(pokemon.getTypes())
                ))
                .toList();
        if (abilityPokemonIds.size() == abilityPokemonResponses.size()) {
            return AbilityDetailResponse.of(ability, abilityPokemonResponses);
        }

        throw new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND);
    }

    private List<AbilityTypeResponse> getAbilityTypeResponses(List<Type> types) {
        return types.stream()
                .map(AbilityTypeResponse::from)
                .toList();
    }
}
