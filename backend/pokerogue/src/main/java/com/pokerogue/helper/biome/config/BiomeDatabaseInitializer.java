package com.pokerogue.helper.biome.config;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.BiomeLink;
import com.pokerogue.helper.biome.data.BiomePokemon;
import com.pokerogue.helper.biome.data.BiomeTypeAndTrainer;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.data.TrainerPokemon;
import com.pokerogue.helper.biome.data.TrainerType;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BiomeDatabaseInitializer implements ApplicationRunner {

    private final BiomeRepository biomeRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<BiomePokemon> biomePokemons = new ArrayList<>();
        List<BiomeLink> biomeLinks = new ArrayList<>();
        List<BiomeTypeAndTrainer> biomeTypesAndTrainers = new ArrayList<>();
        List<TrainerType> trainerTypes = new ArrayList<>();
        List<TrainerPokemon> trainerPokemons = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-pokemons.txt")
        )) {
            while (true) {
                String biomePokemon = bufferedReader.readLine();
                if (biomePokemon == null) {
                    break;
                }
                biomePokemons.add(new BiomePokemon(biomePokemon));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-links.txt")
        )) {
            while (true) {
                String biomeLink = bufferedReader.readLine();
                if (biomeLink == null) {
                    break;
                }
                biomeLinks.add(new BiomeLink(biomeLink));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/biome-types-trainers.txt")
        )) {
            while (true) {
                String biomeTypeAndTrainer = bufferedReader.readLine();
                if (biomeTypeAndTrainer == null) {
                    break;
                }
                biomeTypesAndTrainers.add(new BiomeTypeAndTrainer(biomeTypeAndTrainer));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-types.txt")
        )) {
            while (true) {
                String trainerType = bufferedReader.readLine();
                if (trainerType == null) {
                    break;
                }
                trainerTypes.add(new TrainerType(trainerType));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "src/main/java/com/pokerogue/helper/biome/config/trainer-pokemons.txt")
        )) {
            while (true) {
                String trainerPokemon = bufferedReader.readLine();
                if (trainerPokemon == null) {
                    break;
                }
                trainerPokemons.add(new TrainerPokemon(trainerPokemon));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        List<Trainer> trainers = trainerTypes.stream()
                .map(trainerType -> new Trainer(trainerType.getTrainerName(), trainerType.getTrainerTypes(),
                        getTrainerPokemons(trainerPokemons, trainerType.getTrainerName())))
                .toList();

        biomeTypesAndTrainers.stream()
                .map(biomeTypeAndTrainer -> new Biome(
                        biomeTypeAndTrainer.getBiomeName(),
                        biomeTypeAndTrainer.getBiomeName(),
                        getBiomePokemons(biomePokemons, biomeTypeAndTrainer.getBiomeName()),
                        biomeTypeAndTrainer.getBiomeTypes(),
                        getBiomeTrainers(trainers, biomeTypeAndTrainer.getTrainerNames()),
                        getNextBiomes(biomeLinks, biomeTypeAndTrainer.getBiomeName()))
                )
                .forEach(biomeRepository::save);
    }

    private Map<Tier, List<String>> getBiomePokemons(List<BiomePokemon> biomePokemons, String biomeName) {
        List<BiomePokemon> allBiomePokemons = biomePokemons.stream()
                .filter(biomePokemon -> biomePokemon.getBiomeName().equals(biomeName))
                .toList();

        Map<Tier, List<String>> ret = new HashMap<>();
        for (BiomePokemon biomePokemon : allBiomePokemons) {
            if (ret.containsKey(biomePokemon.getPokemonTier())) {
                List<String> pokemons = ret.get(biomePokemon.getPokemonTier());
                pokemons.addAll(biomePokemon.getPokemons());
            } else {
                ret.put(biomePokemon.getPokemonTier(), new ArrayList<>(biomePokemon.getPokemons()));
            }
        }

        return ret;
    }

    private List<Trainer> getBiomeTrainers(List<Trainer> trainers, List<String> trainerNames) {
        return trainers.stream()
                .filter(trainer -> trainerNames.contains(trainer.getName()))
                .toList();
    }

    private List<String> getNextBiomes(List<BiomeLink> biomeLinks, String biomeName) {
        return biomeLinks.stream()
                .filter(biomeLink -> biomeLink.getCurrentBiome().equals(biomeName))
                .map(BiomeLink::getNextBiomes)
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));
    }

    private List<String> getTrainerPokemons(List<TrainerPokemon> trainerPokemons, String trainerName) {
        return trainerPokemons.stream()
                .filter(trainerPokemon -> trainerPokemon.getTrainerName().equals(trainerName))
                .map(TrainerPokemon::getTrainerPokemons)
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TRAINER_NOT_FOUND));
    }
}
