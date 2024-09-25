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
    public static PokemonResponse from(Pokemon pokemon, String image, String backImage,
                                       List<PokemonTypeResponse> pokemonTypeResponse) {

        return new PokemonResponse(
                pokemon.getId(),
                (long) pokemon.getPokedexNumber(), //todo: DTO를 바꿀 것인지?
                pokemon.getKoName(),
                pokemon.getFormName(),
                image,
                backImage,
                pokemonTypeResponse,
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
