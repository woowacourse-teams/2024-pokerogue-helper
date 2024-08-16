package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Trainer;
import java.util.List;

public record TrainerPokemonResponse(
        String trainerName,
        String trainerImage,
        List<String> trainerTypeLogos,
        List<String> pokemons
) {

    public static TrainerPokemonResponse from(Trainer trainer) {
        return new TrainerPokemonResponse(
                trainer.getName(),
                trainer.getImage(),
                trainer.getTrainerTypes(),
                trainer.getPokemons()
        );
    }
}
