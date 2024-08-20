package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record PokemonDetailResponse(
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
        List<EvolutionResponse> evolutions,
        List<MoveResponse> moves,
        List<EggMoveResponse> eggMoveResponses,
        List<BiomeResponse> biomes
) {
}
