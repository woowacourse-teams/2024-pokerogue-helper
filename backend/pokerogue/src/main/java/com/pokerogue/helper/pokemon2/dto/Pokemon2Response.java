package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record Pokemon2Response(
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
    public static Pokemon2Response from(Pokemon pokemon, String image, String backImage, List<PokemonTypeResponse> pokemonTypeResponse) {

        return new Pokemon2Response(
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
