package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.data.Type;
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
    public static Pokemon2Response from(Pokemon pokemon, String image, String typeLogo1, String typeLogo2) {

        return new Pokemon2Response(
                pokemon.id(),
                Long.parseLong(pokemon.speciesId()),
                pokemon.koName(),
                pokemon.formName(),
                image,
                List.of(
                        new PokemonTypeResponse(Type.findById(pokemon.type1()).getName(), typeLogo1),
                        new PokemonTypeResponse(Type.findById(pokemon.type2()).getName(), typeLogo2)
                ),
                pokemon.generation(),
                pokemon.baseTotal(),
                pokemon.hp(),
                pokemon.attack(),
                pokemon.defense(),
                pokemon.specialAttack(),
                pokemon.specialDefense(),
                pokemon.speed()
        );
    }
}
