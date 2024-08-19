package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.List;

public record BiomeDetailResponse(
        String id,
        String name,
        String image,
        List<BiomeAllPokemonResponse> wildPokemons,
        List<BiomeAllPokemonResponse> bossPokemons,
        List<TrainerPokemonResponse> trainerPokemons,
        List<NextBiomeResponse> map
) {
    public static BiomeDetailResponse of(
            Biome biome,
            List<BiomeAllPokemonResponse> wildPokemons,
            List<BiomeAllPokemonResponse> bossPokemons,
            List<TrainerPokemonResponse> trainerPokemons,
            List<NextBiomeResponse> map
    ) {
        return new BiomeDetailResponse(
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
