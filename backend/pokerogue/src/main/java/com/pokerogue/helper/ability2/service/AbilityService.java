package com.pokerogue.helper.ability2.service;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability2.data.Ability;
import com.pokerogue.helper.ability2.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability2.dto.AbilityPokemonResponse;
import com.pokerogue.helper.ability2.dto.AbilityResponse2;
import com.pokerogue.helper.ability2.dto.AbilityTypeResponse;
import com.pokerogue.helper.ability2.repository.AbilityRepository;
import com.pokerogue.helper.battle.Type;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final S3Service s3Service;
    private final AbilityRepository abilityRepository;

    public List<AbilityResponse2> findAbilities() {
        return abilityRepository.findAll().stream()
                .map(AbilityResponse2::from)
                .toList();
    }

    public AbilityDetailResponse findAbilityDetails(String id) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
        List<AbilityPokemonResponse> abilityPokemonResponses = ability.getPokemons().stream()
                .map(abilityPokemon -> new AbilityPokemonResponse(
                        abilityPokemon.id(),
                        Long.parseLong(abilityPokemon.speciesId()),
                        abilityPokemon.koName(),
                        s3Service.getPokemonImageFromS3(abilityPokemon.id()),
                        getAbilityTypeResponses(abilityPokemon.firstType(), abilityPokemon.secondType())
                ))
                .toList();

        return AbilityDetailResponse.of(ability, abilityPokemonResponses);
    }

    private List<AbilityTypeResponse> getAbilityTypeResponses(String firstType, String secondType) {
        List<AbilityTypeResponse> abilityTypeResponses = new ArrayList<>();
        if (!firstType.equals("Type.undefined") && !firstType.isEmpty()) {
            abilityTypeResponses.add(new AbilityTypeResponse(Type.findByEngName(firstType)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND)).getImage(),
                    firstType));
        }
        if (!secondType.equals("Type.undefined") && !secondType.isEmpty()) {
            abilityTypeResponses.add(new AbilityTypeResponse(Type.findByEngName(secondType)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND)).getImage(),
                    secondType));
        }

        return abilityTypeResponses;
    }
}
