package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.biome.data.Tier;
import java.util.List;
import lombok.Getter;

@Getter
public class BiomePokemon {

    private final String biomeName;
    private final Tier pokemonTier;
    private final List<String> pokemons;

    public BiomePokemon(String biomePokemon) {
        String[] biomePokemonInforms = biomePokemon.split(" / ");
        this.biomeName = biomePokemonInforms[0];
        this.pokemonTier = Tier.getTierByName(biomePokemonInforms[2]);
        this.pokemons = List.of(biomePokemonInforms[3].split(","));
    }
}
