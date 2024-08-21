package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Trainer;
import java.util.List;

public record TrainerPokemonResponse(
        String trainerName,
        String trainerImage,
        List<String> trainerTypeLogos,
        List<BiomePokemonResponse> pokemons
) {

    public static TrainerPokemonResponse from(
            Trainer trainer,
            List<String> trainerTypes,
            List<BiomePokemonResponse> trainerPokemons) {
        return new TrainerPokemonResponse(
                trainer.getName(),
                trainer.getImage(),
                trainerTypes,
                trainerPokemons
        );
    }
}