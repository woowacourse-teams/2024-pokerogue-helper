package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import jakarta.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public record Pokemon2Response(
        String id,
        Long pokedexNumber,
        String name,
        String formName,
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
        List<Integer> stats = Arrays.stream(pokemon.baseStats().split(","))
                .map(Integer::parseInt)
                .toList();

        return new Pokemon2Response(
                pokemon.id(),
                Long.parseLong(pokemon.speciesId()),
                pokemon.koName(),
                pokemon.formName(),
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
                Integer.parseInt(pokemon.generation()),
                Integer.parseInt(pokemon.baseTotal()),
                stats.get(0),
                stats.get(1),
                stats.get(2),
                stats.get(3),
                stats.get(4),
                stats.get(5)
        );
    }
}
