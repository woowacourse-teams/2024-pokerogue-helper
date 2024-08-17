package com.pokerogue.helper.biome.service;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.dto.BiomePokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse2;
import com.pokerogue.helper.biome.dto.NextBiomeResponse;
import com.pokerogue.helper.biome.dto.TrainerPokemonResponse;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final BiomeRepository biomeRepository;

    public List<BiomeResponse> findBiomes() {
        return biomeRepository.findAll().stream()
                .map(BiomeResponse::from)
                .toList();
    }

    public BiomeResponse2 findBiome(String id) {
        Biome biome = biomeRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));
        return BiomeResponse2.of(
                biome,
                getWildPokemons(biome),
                getBossPokemons(biome),
                getTrainerPokemons(biome),
                getNextBiomes(biome));
    }

    private List<BiomePokemonResponse> getWildPokemons(Biome biome) {
        Map<Tier, List<String>> biomePokemons = biome.getPokemons();
        return biomePokemons.keySet().stream()
                .filter(Tier::isWildPokemon)
                .map(tier -> BiomePokemonResponse.of(tier, biomePokemons.get(tier)))
                .toList();
    }

    private List<BiomePokemonResponse> getBossPokemons(Biome biome) {
        Map<Tier, List<String>> biomePokemons = biome.getPokemons();
        return biomePokemons.keySet().stream()
                .filter(Tier::isBossPokemon)
                .map(tier -> BiomePokemonResponse.of(tier, biomePokemons.get(tier)))
                .toList();
    }

    private List<TrainerPokemonResponse> getTrainerPokemons(Biome biome) {
        return biome.getTrainers().stream()
                .map(TrainerPokemonResponse::from)
                .toList();
    }

    private List<NextBiomeResponse> getNextBiomes(Biome biome) {
        return biome.getNextBiome().stream()
                .map(id -> new NextBiomeResponse(id, biomeRepository.findById(id)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND)).getName()))
                .toList();
    }
}
