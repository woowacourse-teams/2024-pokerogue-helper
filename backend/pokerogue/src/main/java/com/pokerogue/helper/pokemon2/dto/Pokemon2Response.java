package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import jakarta.annotation.Nullable;
import java.util.List;

public record Pokemon2Response(
        String id,
        Long pokedexNumber,
        String name,
        String image,
        List<PokemonTypeResponse> pokemonTypeResponses,
        Integer generation,
        Integer totalStats,
        Integer hp,
        Integer speed,
        Integer attack,
        Integer defense,
        Integer specialAttack,
        Integer specialDefense
) {
    public static Pokemon2Response from(Pokemon pokemon) {

        return new Pokemon2Response(
                pokemon.id(),
                0L,
                pokemon.koName(),
                "image",
                List.of(
                        new PokemonTypeResponse(
                                pokemon.type1(),
                                "image"
                        ),
                        new PokemonTypeResponse(
                                pokemon.type2(),
                                "image"
                        )
                ),
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
        );
    }
}
