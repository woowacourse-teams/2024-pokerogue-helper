package com.pokerogue.helper.pokemon.data;

import java.util.List;

class PokemonValidator extends Validator{
    private static int POKEMON_SIZE = 1446;
    private static int POKEMON_ID_POLICY = 1394;

    private PokemonValidator() {
    }

    static void validatePokemonSize(List<Pokemon> pokemons) {
        if(pokemons.size() != POKEMON_SIZE){
            throw new IllegalArgumentException("Pokemon size is not equal to " + POKEMON_SIZE);
        }
    }

    static void validate(List<Pokemon> pokemons) {
        List<String> ids = pokemons.stream().map(Pokemon::getId).toList();
        throwIfNull(pokemons);

        throwIfIdDuplicates(ids);
    }

}
