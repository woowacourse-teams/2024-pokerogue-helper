package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.dto.BiomeResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BiomeRepository {

    private final Map<String, Map<String, Map<String, List<String>>>> biomes;
    private final Map<String, List<String>> biomeLinks;
    private final Map<String, List<String>> trainerSpecies;
    private final Map<String, Map<String, List<String>>> biomeTrainers;
    private final Map<String, List<String>> mainTypes;
    private final Map<String, List<String>> trainers;
    private final Map<String, List<String>> trainerTypes;

    public BiomeRepository() {
        this.biomes = new HashMap<>();
        this.biomeLinks = new HashMap<>();
        this.trainerSpecies = new HashMap<>();
        this.biomeTrainers = new HashMap<>();
        this.mainTypes = new HashMap<>();
        this.trainers = new HashMap<>();
        this.trainerTypes = new HashMap<>();
    }

    public void saveBiome(String biome) {
        System.out.println(biome);
        String[] biomeInforms = biome.split(" / ");
        if (biomes.containsKey(biomeInforms[0])) {
            Map<String, Map<String, List<String>>> biomeTimeOfDays = biomes.get(biomeInforms[0]);
            if (biomeTimeOfDays.containsKey(biomeInforms[1])) {
                Map<String, List<String>> biomeTiers = biomeTimeOfDays.get(biomeInforms[1]);
                if (biomeTiers.containsKey(biomeInforms[2])) {
                    List<String> pokemons = biomeTiers.get(biomeInforms[2]);
                    pokemons.addAll(List.of(biomeInforms[3].split(",")));
                } else {
                    List<String> pokemons = new ArrayList<>(List.of(biomeInforms[3].split(",")));
                    biomeTiers.put(biomeInforms[2], pokemons);
                }
            } else {
                Map<String, List<String>> biomeTiers = new HashMap<>();
                List<String> pokemons = new ArrayList<>(List.of(biomeInforms[3].split(",")));
                biomeTiers.put(biomeInforms[2], pokemons);
                biomeTimeOfDays.put(biomeInforms[1], biomeTiers);
            }
        } else {
            Map<String, Map<String, List<String>>> biomeTimeOfDays = new HashMap<>();
            Map<String, List<String>> biomeTiers = new HashMap<>();
            List<String> pokemons = new ArrayList<>(List.of(biomeInforms[3].split(",")));
            biomeTiers.put(biomeInforms[2], pokemons);
            biomeTimeOfDays.put(biomeInforms[1], biomeTiers);
            biomes.put(biomeInforms[0], biomeTimeOfDays);
        }
    }

    public void saveBiomeLink(String biomeLink) {
        System.out.println(biomeLink);
        String[] biomeLinkInforms = biomeLink.split(" / ");
        if (biomeLinks.containsKey(biomeLinkInforms[0])) {
            List<String> nextBiomes = biomeLinks.get(biomeLinkInforms[0]);
            nextBiomes.addAll(List.of(biomeLinkInforms[1].split(",")));
            biomeLinks.put(biomeLinkInforms[0], nextBiomes);
        } else {
            List<String> nextBiomes = new ArrayList<>(List.of(biomeLinkInforms[1].split(",")));
            biomeLinks.put(biomeLinkInforms[0], nextBiomes);
        }
    }
    public void saveTrainerSpecie(String trainerSpecie) {
        System.out.println(trainerSpecie);
        String[] trainerSpecieInforms = trainerSpecie.split(" / ");
        if (trainerSpecies.containsKey(trainerSpecieInforms[0])) {
            List<String> pokemons = trainerSpecies.get(trainerSpecieInforms[0]);
            pokemons.addAll(List.of(trainerSpecieInforms[1].split(",")));
            trainerSpecies.put(trainerSpecieInforms[0], pokemons);
        } else {
            List<String> pokemons = new ArrayList<>(List.of(trainerSpecieInforms[1].split(",")));
            trainerSpecies.put(trainerSpecieInforms[0], pokemons);
        }
    }

    public void saveBiomeTrainer(String biomeTrainer) {
        System.out.println(biomeTrainer);
        String[] biomeTrainerInforms = biomeTrainer.split(" / ");
        if (biomeTrainers.containsKey(biomeTrainerInforms[0])) {
            Map<String, List<String>> trainerTiers = biomeTrainers.get(biomeTrainerInforms[0]);
            if (trainerTiers.containsKey(biomeTrainerInforms[1])) {
                List<String> trainers = trainerTiers.get(biomeTrainerInforms[1]);
                trainers.addAll(List.of(biomeTrainerInforms[3].split(",")));
                biomeTrainers.put(biomeTrainerInforms[0], trainerTiers);
            } else {
                List<String> trainers = new ArrayList<>(List.of(biomeTrainerInforms[2].split(",")));
                trainerTiers.put(biomeTrainerInforms[1], trainers);
            }
        } else {
            Map<String, List<String>> trainerTiers = new HashMap<>();
            List<String> trainers = new ArrayList<>(List.of(biomeTrainerInforms[2].split(",")));
            trainerTiers.put(biomeTrainerInforms[1], trainers);
            biomeTrainers.put(biomeTrainerInforms[0], trainerTiers);
        }
    }


    public void saveBiomeType(String biomeType) {
        System.out.println(biomeType);
        String[] biomeTypeInforms = biomeType.split(" / ");
        mainTypes.put(biomeTypeInforms[0], List.of(biomeTypeInforms[1].split(",")));
        trainers.put(biomeTypeInforms[0], List.of(biomeTypeInforms[2].split(",")));
    }

    public void saveTrainerType(String trainerType) {
        System.out.println(trainerType);
        String[] trainerTypeInforms = trainerType.split(" / ");
        trainerTypes.put(trainerTypeInforms[0], List.of(trainerTypeInforms[1].split(",")));
    }

    public List<BiomeResponse> findAllBiomes() {
        List<BiomeResponse> biomeResponses = new ArrayList<>();
        for (String biome : biomes.keySet()) {
            List<String> biomeMainType = mainTypes.get(biome);
            List<String> biomeTrainers = trainers.get(biome);
            Set<String> biomeTrainerTypes = new HashSet<>();
            biomeTrainers.stream()
                    .forEach(biomeTrainer -> biomeTrainerTypes.addAll(trainerTypes.get(biomeTrainer)));
            List<String> biomeTrainerTypeList = biomeTrainerTypes.stream().toList();
            BiomeResponse biomeResponse = new BiomeResponse(biome, biome.replace("_", " "), "바이옴 이미지", biomeMainType,
                    biomeTrainerTypeList);
            biomeResponses.add(biomeResponse);
        }

        return biomeResponses;
    }
}
