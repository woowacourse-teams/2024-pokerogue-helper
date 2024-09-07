package com.pokerogue.helper.biome.service;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.dto.BiomeAllPokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeDetailResponse;
import com.pokerogue.helper.biome.dto.BiomePokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.dto.BiomeTypeResponse;
import com.pokerogue.helper.biome.dto.NextBiomeResponse;
import com.pokerogue.helper.biome.dto.TrainerPokemonResponse;
import com.pokerogue.helper.biome.repository.BiomePokemonTypeImageRepository;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon2.data.Type;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final S3Service s3Service;
    private final BiomeRepository biomeRepository;
    private final Pokemon2Repository pokemon2Repository;
    private final BiomePokemonTypeImageRepository biomePokemonTypeImageRepository;

    public List<BiomeResponse> findBiomes() {
        return biomeRepository.findAll().stream()
                .map(biome -> BiomeResponse.of(
                        biome,
                        getTypesResponses(biome.getMainTypes()),
                        getTypesResponses(biome.getTrainerTypes()))
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
                .map(biomePokemon -> pokemon2Repository.findById(biomePokemon)
                            .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND))
                )
                .map(biomePokemonInfo -> new BiomePokemonResponse(
                        biomePokemonInfo.id(),
                        biomePokemonInfo.koName(),
                        s3Service.getPokemonImageFromS3(biomePokemonInfo.id()),
                        getBiomePokemonTypeResponses(
                                Type.findById(biomePokemonInfo.type1()),
                                Type.findById(biomePokemonInfo.type2()))
                ))
                .distinct()
                .toList();
    }

    private List<BiomeTypeResponse> getBiomePokemonTypeResponses(
            Type type1,
            Type type2
    ) {
        List<BiomeTypeResponse> biomeTypeRespons = new ArrayList<>();
        if (!type1.getName().isEmpty() && !type1.getName().equals("Unknown")) {
            biomeTypeRespons.add(new BiomeTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(type1.name()),
                    type1.getName())
            );
        }
        if (!type2.getName().isEmpty() && !type2.getName().equals("Unknown")) {
            biomeTypeRespons.add(new BiomeTypeResponse(
                    biomePokemonTypeImageRepository.findPokemonTypeImageUrl(type2.name()),
                    type2.getName())
            );
        }

        return biomeTypeRespons;
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
                        getTypesResponses(trainer.getTrainerTypes()),
                        getBiomePokemons(trainer.getPokemons()))
                )
                .toList();
    }

    private List<BiomeTypeResponse> getTypesResponses(List<String> types) {
        return types.stream()
                .map(type -> new BiomeTypeResponse(biomePokemonTypeImageRepository.findPokemonTypeImageUrl(
                        BiomePokemonType.getBiomePokemonTypeByName(type).name()), type)
                )
                .toList();
    }

    private List<NextBiomeResponse> getNextBiomes(Biome biome) {
        if (biome.getId().equals("end")) {
            return List.of();
        }

        return biome.getNextBiome().stream()
                .map(nextBiomeInfo -> {
                    Biome nextBiome = biomeRepository.findById(nextBiomeInfo.getId())
                            .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));
                    return NextBiomeResponse.of(
                            nextBiome,
                            nextBiomeInfo.getPercent(),
                            getTypesResponses(nextBiome.getMainTypes()),
                            getTypesResponses(nextBiome.getTrainerTypes())
                    );
                })
                .toList();
    }
}
