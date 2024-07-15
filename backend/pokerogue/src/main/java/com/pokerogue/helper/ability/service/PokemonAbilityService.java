package com.pokerogue.helper.ability.service;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
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
}
