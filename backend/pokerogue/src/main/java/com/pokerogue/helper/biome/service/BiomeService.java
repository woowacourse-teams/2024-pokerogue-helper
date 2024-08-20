package com.pokerogue.helper.biome.service;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.dto.BiomeAllPokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeDetailResponse;
import com.pokerogue.helper.biome.dto.BiomePokemonResponse;
import com.pokerogue.helper.biome.dto.BiomePokemonTypeResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.dto.NextBiomeResponse;
import com.pokerogue.helper.biome.dto.TrainerPokemonResponse;
import com.pokerogue.helper.biome.repository.BiomePokemonInfoRepository;
import com.pokerogue.helper.biome.repository.BiomePokemonTypeImageRepository;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final BiomeRepository biomeRepository;
    private final BiomePokemonInfoRepository biomePokemonInfoRepository;
    private final BiomePokemonTypeImageRepository biomePokemonTypeImageRepository;

    public List<BiomeResponse> findBiomes() {
        return biomeRepository.findAll().stream()
                .map(biome -> BiomeResponse.from(
                        biome,
                        getTrainerTypes(biome.getMainTypes()),
                        getTrainerTypes(biome.getTrainerTypes()))
                )
                .toList();
    }

    public BiomeDetailResponse findBiome(String id) {
        Biome biome = biomeRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));

        return BiomeDetailResponse.of(
                biome,
                getWildPokemons(biome),
                getBossPokemons(biome),
                getTrainerPokemons(biome),
                getNextBiomes(biome)
        );
    }

    private List<BiomeAllPokemonResponse> getWildPokemons(Biome biome) {
        Map<Tier, List<String>> biomePokemons = biome.getPokemons();

        return biomePokemons.keySet().stream()
                .filter(Tier::isWildPokemon)
                .map(tier -> BiomeAllPokemonResponse.of(tier, getBiomePokemons(biomePokemons.get(tier))))
                .distinct()
                .toList();
    }

    private List<BiomeAllPokemonResponse> getBossPokemons(Biome biome) {
        Map<Tier, List<String>> biomePokemons = biome.getPokemons();

        return biomePokemons.keySet().stream()
                .filter(Tier::isBossPokemon)
                .map(tier -> BiomeAllPokemonResponse.of(tier, getBiomePokemons(biomePokemons.get(tier))))
                .distinct()
                .toList();
    }

    private List<BiomePokemonResponse> getBiomePokemons(List<String> biomePokemons) {
        return biomePokemons.stream()
                .map(biomePokemon -> biomePokemonInfoRepository.findById(biomePokemon)
                            .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND))
                )
                .map(biomePokemonInfo -> new BiomePokemonResponse(
                        biomePokemonInfo.getId(),
                        biomePokemonInfo.getName(),
                        biomePokemonInfo.getImage(),
                        getBiomePokemonTypeResponses(biomePokemonInfo.getType1(), biomePokemonInfo.getType2())
                ))
                .distinct()
                .toList();
    }

    private List<BiomePokemonTypeResponse> getBiomePokemonTypeResponses(
            BiomePokemonType type1,
            BiomePokemonType type2
    ) {
        List<BiomePokemonTypeResponse> biomePokemonTypeResponses = new ArrayList<>();
        if (!type1.getName().equals("없음")) {
            biomePokemonTypeResponses.add(new BiomePokemonTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(type1.name()),
                    type1.getName())
            );
        }
        if (!type2.getName().equals("없음")) {
            biomePokemonTypeResponses.add(new BiomePokemonTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(type2.name()),
                    type2.getName())
            );
        }

        return biomePokemonTypeResponses;
    }

    private List<TrainerPokemonResponse> getTrainerPokemons(Biome biome) {
        List<String> trainerNames = biome.getTrainers().stream()
                .map(Trainer::getName)
                .toList();
        if (trainerNames.contains("없음")) {
            return List.of();
        }
        return biome.getTrainers().stream()
                .map(trainer -> TrainerPokemonResponse.from(
                        trainer,
                        getTrainerTypes(trainer.getTrainerTypes()),
                        getBiomePokemons(trainer.getPokemons()))
                )
                .toList();
    }

    private List<String> getTrainerTypes(List<String> trainerTypes) {
        return trainerTypes.stream()
                .map(trainerType -> biomePokemonTypeImageRepository.findPokemonTypeImageUrl(
                        BiomePokemonType.getBiomePokemonTypeByName(trainerType).name())
                )
                .toList();
    }

    private List<NextBiomeResponse> getNextBiomes(Biome biome) {
        return biome.getNextBiome().stream()
                .map(id -> new NextBiomeResponse(id, biomeRepository.findById(id)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND)).getName()))
                .toList();
    }
}
