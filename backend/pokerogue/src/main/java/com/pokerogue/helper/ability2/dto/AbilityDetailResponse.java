package com.pokerogue.helper.ability2.dto;

import com.pokerogue.helper.ability2.data.Ability;
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
