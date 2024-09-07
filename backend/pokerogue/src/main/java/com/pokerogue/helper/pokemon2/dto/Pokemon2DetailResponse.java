package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record Pokemon2DetailResponse(
        String id,
        Long pokedexNumber,
        String name,
        String pokemonImage,
        List<PokemonTypeResponse> pokemonTypeResponses,
        List<PokemonAbilityResponse> pokemonAbilityResponses,
        int totalStats,
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed,
        Boolean legendary,
        Boolean subLegendary,
        Boolean mythical,
        Boolean canChangeForm,
        Double weight,
        Double height,
        EvolutionResponses evolutions,
        List<MoveResponse> moves,
        List<MoveResponse> eggMoveResponses,
        List<BiomeResponse> biomes
) {
}
