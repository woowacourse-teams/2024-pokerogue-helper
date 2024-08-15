package com.pokerogue.helper.biome.config;

import com.pokerogue.helper.biome.domain.BiomeLink;
import com.pokerogue.helper.biome.domain.BiomePokemon;
import com.pokerogue.helper.biome.domain.BiomeTypeAndTrainer;
import com.pokerogue.helper.biome.domain.TrainerPokemon;
import com.pokerogue.helper.biome.domain.TrainerType;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BiomeDatabaseInitializer implements ApplicationRunner {

    private final BiomeRepository biomeRepository;
    private final List<BiomePokemon> biomePokemons;
    private final List<BiomeLink> biomeLinks;
    private final List<BiomeTypeAndTrainer> biomeTypesAndTrainers;
    private final List<TrainerType> trainerTypes;
    private final List<TrainerPokemon> trainerPokemons;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-pokemons.txt")
        );
        while (true) {
            String biomePokemon = bufferedReader.readLine();
            if (biomePokemon == null) {
                break;
            }
            biomePokemons.add(new BiomePokemon(biomePokemon));
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-links.txt")
        );
        while (true) {
            String biomeLink = bufferedReader.readLine();
            if (biomeLink == null) {
                break;
            }
            biomeLinks.add(new BiomeLink(biomeLink));
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-types-trainers.txt")
        );
        while (true) {
            String biomeTypeAndTrainer = bufferedReader.readLine();
            if (biomeTypeAndTrainer == null) {
                break;
            }
            biomeTypesAndTrainers.add(new BiomeTypeAndTrainer(biomeTypeAndTrainer));
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-types.txt")
        );
        while (true) {
            String trainerType = bufferedReader.readLine();
            if (trainerType == null) {
                break;
            }
            trainerTypes.add(new TrainerType(trainerType));
        }

        bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-pokemons.txt")
        );
        while (true) {
            String trainerPokemon = bufferedReader.readLine();
            if (trainerPokemon == null) {
                break;
            }
            trainerPokemons.add(new TrainerPokemon(trainerPokemon));
        }
    }
}
