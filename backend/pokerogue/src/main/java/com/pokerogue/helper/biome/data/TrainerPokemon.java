package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class TrainerPokemon {

    private final String trainerName;
    private final List<String> trainerPokemons;

    public TrainerPokemon(String trainerPokemon) {
        String[] trainerPokemonInforms = trainerPokemon.split(" / ");
        this.trainerName = trainerPokemonInforms[0];
        this.trainerPokemons = List.of(trainerPokemonInforms[1].split(","));
    }
}
