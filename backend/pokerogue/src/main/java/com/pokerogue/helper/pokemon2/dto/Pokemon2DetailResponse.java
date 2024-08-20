package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Builder;

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
        EvolutionResponse evolutions,
        List<MoveResponse> moves,
        List<MoveResponse> eggMoveResponses,
        List<BiomeResponse> biomes
) {
}
