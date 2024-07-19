package com.pokerogue.helper.ability.service;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
