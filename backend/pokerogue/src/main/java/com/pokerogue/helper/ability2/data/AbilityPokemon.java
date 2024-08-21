package com.pokerogue.helper.ability2.data;

import com.pokerogue.helper.biome.data.BiomePokemonType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AbilityPokemon {

    private final String id;
    private final Long pokedexNumber;
    private final String name;
    private final String type1;
    private final String type2;
}
