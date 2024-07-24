package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record PokedexResponse(
        Long pokedexNumber,
        String koName,
        String pokemonImage,
        List<PokemonTypeResponse> pokemonTypeResponses,
        List<String> pokemonAbilityNames,
        Double weight,
        Double height,
        Integer attack,
        Integer defense,
        Integer specialAttack,
        Integer specialDefense,
        Integer hp,
        Integer speed,
        Integer totalStats
) {

    public static PokedexResponse of(
            Pokemon pokemon,
            List<PokemonTypeResponse> pokemonTypeResponses,
            List<String> pokemonAbilityNames
    ) {
        return new PokedexResponse(pokemon.getPokedexNumber(), pokemon.getKoName(), pokemon.getImage(),
                pokemonTypeResponses, pokemonAbilityNames, pokemon.getWeight(), pokemon.getHeight(),
                pokemon.getAttack(), pokemon.getDefense(), pokemon.getSpecialAttack(), pokemon.getSpecialDefense(),
                pokemon.getHp(), pokemon.getSpeed(), pokemon.getTotalStats());
    }
}
