package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

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
        EvolutionResponses evolutions,
        List<MoveResponse> moves,
        List<EggMoveResponse> eggMoveResponses,
        List<PokemonBiomeResponse> biomes
) {
    public static PokemonDetailResponse from(
            Pokemon pokemon,
            List<PokemonTypeResponse> pokemonTypeResponses,
            List<PokemonAbilityResponse> pokemonAbilityResponses,
            List<MoveResponse> moveResponse,
            List<EggMoveResponse> eggMoveResponse,
            List<PokemonBiomeResponse> biomeResponse,
            EvolutionResponses evolutionResponses
    ) {
        return new PokemonDetailResponse(
                pokemon.getId(),
                (long) pokemon.getPokedexNumber(),
                pokemon.getKoName(),
                pokemon.getImageId(),
                pokemonTypeResponses,
                pokemonAbilityResponses,
                pokemon.getBaseTotal(),
                pokemon.getHp(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getSpecialAttack(),
                pokemon.getSpecialDefense(),
                pokemon.getSpeed(),
                pokemon.isLegendary(),
                pokemon.isSubLegendary(),
                pokemon.isMythical(),
                pokemon.isCanChangeForm(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                evolutionResponses,
                moveResponse,
                eggMoveResponse,
                biomeResponse
        );
    }
}
