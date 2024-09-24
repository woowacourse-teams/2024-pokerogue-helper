package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
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
    public static PokemonResponse from(InMemoryPokemon inMemoryPokemon, String image, String backImage, List<PokemonTypeResponse> pokemonTypeResponse) {

        return new PokemonResponse(
                inMemoryPokemon.id(),
                Long.parseLong(inMemoryPokemon.speciesId()),
                inMemoryPokemon.koName(),
                inMemoryPokemon.formName(),
                image,
                backImage,
                pokemonTypeResponse,
                inMemoryPokemon.generation(),
                inMemoryPokemon.baseTotal(),
                inMemoryPokemon.hp(),
                inMemoryPokemon.attack(),
                inMemoryPokemon.defense(),
                inMemoryPokemon.specialAttack(),
                inMemoryPokemon.specialDefense(),
                inMemoryPokemon.speed()
        );
    }
}
