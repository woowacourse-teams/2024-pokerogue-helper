package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Tier;
import java.util.List;

public record BiomeAllPokemonResponse(String tier, List<BiomePokemonResponse> pokemons) {

    public static BiomeAllPokemonResponse of(Tier tier, List<BiomePokemonResponse> pokemons) {
        return new BiomeAllPokemonResponse(tier.getName(), pokemons);
    }

    public static BiomeAllPokemonResponse of(NativePokemon nativePokemon, List<BiomePokemonResponse> pokemons) {
        return new BiomeAllPokemonResponse(nativePokemon.getTier(), pokemons);
    }
}
