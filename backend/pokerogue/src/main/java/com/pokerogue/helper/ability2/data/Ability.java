package com.pokerogue.helper.ability2.data;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Ability {

    private final String id;
    private final String name;
    private final String description;
    private final List<AbilityPokemon> pokemons;
}
