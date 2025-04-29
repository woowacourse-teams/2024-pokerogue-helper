package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.ability.data.Ability;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record PokemonAbilityResponse(String id, String name, String description, Boolean passive, Boolean hidden) {

    public static PokemonAbilityResponse from(Ability ability, boolean isPassive, boolean isHidden) {
        return new PokemonAbilityResponse(
                ability.getId(),
                ability.getName(),
                ability.getDescription(),
                isPassive,
                isHidden
        );
    }


    public static List<PokemonAbilityResponse> createListFrom(List<Optional<Ability>> abilities) {
        final int HIDDEN_INDEX = abilities.size() - 1;
        final int PASSIVE_INDEX = abilities.size() - 2;

        List<Optional<Ability>> normals = abilities.subList(0, HIDDEN_INDEX);
        Optional<Ability> hidden = abilities.get(HIDDEN_INDEX);
        Optional<Ability> passive = abilities.get(PASSIVE_INDEX);

        List<PokemonAbilityResponse> normalResponse = normals.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(normal -> from(normal, false, false))
                .toList();

        Optional<PokemonAbilityResponse> passiveResponse = passive.map(ability -> from(ability, true, false));
        Optional<PokemonAbilityResponse> hiddenResponse = hidden.map(ability -> from(ability, false, true));

        List<PokemonAbilityResponse> totalResponse = new ArrayList<>(normalResponse);
        passiveResponse.ifPresent(totalResponse::add);
        hiddenResponse.ifPresent(totalResponse::add);

        return totalResponse;
    }
}
