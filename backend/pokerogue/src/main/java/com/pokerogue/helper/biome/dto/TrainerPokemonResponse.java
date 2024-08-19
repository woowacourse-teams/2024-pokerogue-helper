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
            String trainerImage,
            List<String> trainerTypes,
            List<BiomePokemonResponse> trainerPokemons) {
        return new TrainerPokemonResponse(
                trainer.getName(),
                trainerImage,
                trainerTypes,
                trainerPokemons
        );
    }
}
