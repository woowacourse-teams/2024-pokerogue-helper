package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.List;

public record BiomeResponse2(
        String id,
        String name,
        String image,
        List<BiomePokemonResponse> wildPokemons,
        List<BiomePokemonResponse> bossPokemons,
        List<TrainerPokemonResponse> trainerPokemons,
        List<NextBiomeResponse> map
) {
    public static BiomeResponse2 of(
            Biome biome,
            List<BiomePokemonResponse> wildPokemons,
            List<BiomePokemonResponse> bossPokemons,
            List<TrainerPokemonResponse> trainerPokemons,
            List<NextBiomeResponse> map
    ) {
        return new BiomeResponse2(
                biome.getId(),
                biome.getName(),
                biome.getImage(),
                wildPokemons,
                bossPokemons,
                trainerPokemons,
                map
        );
    }
}
