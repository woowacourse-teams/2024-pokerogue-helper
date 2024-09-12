package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record PokemonResponse(
        String id,
        Long pokedexNumber,
        String name,
        String formName,
        String image,
        String backImage,
        List<PokemonTypeResponse> pokemonTypeResponse,
        Integer generation,
        Integer totalStats,
        Integer hp,
        Integer speed,
        Integer attack,
        Integer defense,
        Integer specialAttack,
        Integer specialDefense
) {
    public static PokemonResponse from(Pokemon pokemon, String image, String backImage, List<PokemonTypeResponse> pokemonTypeResponse) {

        return new PokemonResponse(
                pokemon.id(),
                Long.parseLong(pokemon.speciesId()),
                pokemon.koName(),
                pokemon.formName(),
                image,
                backImage,
                pokemonTypeResponse,
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
