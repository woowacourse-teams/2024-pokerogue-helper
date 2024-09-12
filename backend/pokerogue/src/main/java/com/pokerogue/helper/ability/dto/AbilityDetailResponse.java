package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.ability.data.Ability;
import java.util.List;

public record AbilityDetailResponse(
        String koName,
        String description,
        List<AbilityPokemonResponse> pokemons
) {

    public static AbilityDetailResponse of(Ability ability, List<AbilityPokemonResponse> pokemons) {
        return new AbilityDetailResponse(ability.getName(), ability.getDescription(), pokemons);
    }
}
