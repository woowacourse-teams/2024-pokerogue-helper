package com.pokerogue.helper.biome.config;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.BiomeLink;
import com.pokerogue.helper.biome.data.BiomePokemon;
import com.pokerogue.helper.biome.data.BiomePokemonInfo;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.biome.data.BiomeTypeAndTrainer;
import com.pokerogue.helper.biome.data.PokemonByBiome;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.data.TrainerPokemon;
import com.pokerogue.helper.biome.data.TrainerType;
import com.pokerogue.helper.biome.repository.BiomePokemonInfoRepository;
import com.pokerogue.helper.biome.repository.BiomePokemonTypeImageRepository;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final S3Service s3Service;
    private final BiomePokemonTypeImageRepository biomePokemonTypeImageRepository;
    private final BiomeRepository biomeRepository;
    private final BiomePokemonInfoRepository biomePokemonInfoRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<BiomePokemon> biomePokemons = new ArrayList<>();
        List<BiomeLink> biomeLinks = new ArrayList<>();
        List<BiomeTypeAndTrainer> biomeTypesAndTrainers = new ArrayList<>();
        List<TrainerType> trainerTypes = new ArrayList<>();
        List<TrainerPokemon> trainerPokemons = new ArrayList<>();
        List<PokemonByBiome> pokemonByBiomes = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("biome-pokemons.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("biome-links.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("biome-types-trainers.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trainer-types.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trainer-pokemons.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pokemon-by-biome.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                String pokemonByBiome = bufferedReader.readLine();
                if (pokemonByBiome == null) {
                    break;
                }
                pokemonByBiomes.add(new PokemonByBiome(pokemonByBiome));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        List<Trainer> trainers = trainerPokemons.stream()
                .filter(trainerPokemon -> existsByTrainerName(trainerTypes, trainerPokemon.getTrainerName()))
                .map(trainerPokemon -> new Trainer(
                        trainerPokemon.getId(),
                        trainerPokemon.getTrainerName(),
                        s3Service.getTrainerImageFromS3(trainerPokemon.getId()),
                        getTrainerTypes(trainerTypes, trainerPokemon.getTrainerName()),
                        trainerPokemon.getTrainerPokemons())
                ).toList();

        biomeTypesAndTrainers.stream()
                .map(biomeTypeAndTrainer -> new Biome(
                        biomeTypeAndTrainer.getId(),
                        biomeTypeAndTrainer.getBiomeName(),
                        s3Service.getBiomeImageFromS3(biomeTypeAndTrainer.getId()),
                        getBiomePokemons(biomePokemons, biomeTypeAndTrainer.getBiomeName()),
                        biomeTypeAndTrainer.getBiomeTypes(),
                        getBiomeTrainers(trainers, biomeTypeAndTrainer.getTrainerNames()),
                        getNextBiomes(biomeLinks, biomeTypeAndTrainer.getId()))
                )
                .forEach(biomeRepository::save);

        pokemonByBiomes.stream()
                .map(pokemonByBiome -> new BiomePokemonInfo(
                        pokemonByBiome.getId(),
                        pokemonByBiome.getName(),
                        s3Service.getPokemonImageFromS3(pokemonByBiome.getId()),
                        BiomePokemonType.getBiomePokemonTypeByName(pokemonByBiome.getType1()),
                        BiomePokemonType.getBiomePokemonTypeByName(pokemonByBiome.getType2())
                ))
                .forEach(biomePokemonInfoRepository::save);

        Arrays.stream(BiomePokemonType.values())
                .forEach(biomePokemonType -> biomePokemonTypeImageRepository.save(
                        biomePokemonType.name(),
                        s3Service.getPokerogueTypeImageFromS3(biomePokemonType.name().toLowerCase()))
                );
    }

    private boolean existsByTrainerName(List<TrainerType> trainerTypes, String trainerName) {
        return trainerTypes.stream()
                .anyMatch(trainerType -> trainerType.getTrainerName().equals(trainerName));
    }

    private List<String> getTrainerTypes(List<TrainerType> trainerTypes, String trainerName) {
        return trainerTypes.stream()
                .filter(trainerType -> trainerType.getTrainerName().equals(trainerName))
                .map(TrainerType::getTrainerTypes)
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TRAINER_NOT_FOUND));
    }

    private Map<Tier, List<String>> getBiomePokemons(List<BiomePokemon> biomePokemons, String biomeName) {
        List<BiomePokemon> allBiomePokemons = biomePokemons.stream()
                .filter(biomePokemon -> biomePokemon.getBiomeName().equals(biomeName))
                .filter(biomePokemon -> !biomePokemon.getPokemons().contains("없음"))
                .toList();

        Map<Tier, List<String>> ret = new HashMap<>();
        for (BiomePokemon biomePokemon : allBiomePokemons) {
            if (biomePokemon.getPokemons().contains("없음")) {
                continue;
            }
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

    private List<String> getNextBiomes(List<BiomeLink> biomeLinks, String biomeId) {
        return biomeLinks.stream()
                .filter(biomeLink -> biomeLink.getId().equals(biomeId))
                .map(BiomeLink::getNextBiomes)
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));
    }
}
