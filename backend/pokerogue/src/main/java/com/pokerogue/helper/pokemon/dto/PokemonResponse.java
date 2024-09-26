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
    public static PokemonResponse from(Pokemon pokemon, List<PokemonTypeResponse> pokemonTypeResponses) {
        return new PokemonResponse(
                pokemon.getId(),
                (long) pokemon.getPokedexNumber(),
                pokemon.getKoName(),
                pokemon.getFormName(),
                pokemon.getImageId(), //image front
                pokemon.getImageId(), //back
                pokemonTypeResponses,
                pokemon.getGeneration(),
                pokemon.getBaseTotal(),
                pokemon.getHp(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getSpecialAttack(),
                pokemon.getSpecialDefense(),
                pokemon.getSpeed()
        );
    }
}
