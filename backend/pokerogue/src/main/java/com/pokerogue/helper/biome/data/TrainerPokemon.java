package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class TrainerPokemon {

    private final String id;
    private final String trainerName;
    private final List<String> trainerPokemons;

    public TrainerPokemon(String trainerPokemon) {
        String[] trainerPokemonInforms = trainerPokemon.split(" / ");
        this.id = trainerPokemonInforms[0];
        this.trainerName = trainerPokemonInforms[1];
        this.trainerPokemons = List.of(trainerPokemonInforms[2].split(","));
    }
}
