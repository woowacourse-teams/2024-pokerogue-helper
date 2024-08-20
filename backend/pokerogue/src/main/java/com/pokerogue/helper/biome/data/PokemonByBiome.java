package com.pokerogue.helper.biome.data;

import lombok.Getter;

@Getter
public class PokemonByBiome {

    private final String id;
    private final String name;
    private final String type1;
    private final String type2;

    public PokemonByBiome(String pokemonByBiome) {
        String[] pokemonByBiomeInforms = pokemonByBiome.split(" / ");
        this.id = pokemonByBiomeInforms[0];
        this.name = pokemonByBiomeInforms[1];
        this.type1 = pokemonByBiomeInforms[2];
        this.type2 = pokemonByBiomeInforms[3];
    }
}
