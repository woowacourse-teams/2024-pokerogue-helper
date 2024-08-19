package com.pokerogue.helper.biome.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BiomePokemonInfo {

    private final String id;
    private final String name;
    private final String image;
    private final BiomePokemonType type1;
    private final BiomePokemonType type2;
}
