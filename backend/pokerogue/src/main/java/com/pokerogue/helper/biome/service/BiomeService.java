package com.pokerogue.helper.biome.service;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.dto.*;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.global.constant.SortingCriteria;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final BiomeRepository biomeRepository;
    private final PokemonRepository pokemonRepository;

    public List<BiomeResponse> findBiomes() {
        return biomeRepository.findAll().stream()
                .map(biome -> BiomeResponse.of(
                        biome,
                        ImageUrl.getBiomeImage(biome.getId()),
                        getTypesResponses(biome.getTypes()),
                        getTrainerTypesResponses(biome.getTrainers()))
                )
                .toList();
    }

    public BiomeDetailResponse findBiome(String id, SortingCriteria bossPokemonOrder, SortingCriteria wildPokemonOrder) {
        Biome biome = biomeRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));

        return BiomeDetailResponse.of(
                biome,
                ImageUrl.getBiomeImage(biome.getId()),
                getWildPokemons(biome.getNativePokemons(), wildPokemonOrder),
                getBossPokemons(biome.getNativePokemons(), bossPokemonOrder),
                getTrainerPokemons(biome),
                getNextBiomes(biome)
        );
    }

    private List<BiomeAllPokemonResponse> getWildPokemons(List<NativePokemon> nativePokemons, SortingCriteria wildPokemonOrder) {
        return nativePokemons.stream()
                .filter(NativePokemon::isWild)
                .sorted(NativePokemonComparator.of(wildPokemonOrder))
                .map(nativePokemon -> BiomeAllPokemonResponse.of(
                        nativePokemon,
                        getBiomePokemons(nativePokemon.getPokemonIds())))
                .toList();
    }

    private List<BiomeAllPokemonResponse> getBossPokemons(List<NativePokemon> nativePokemons, SortingCriteria bossPokemonOrder) {
        return nativePokemons.stream()
                .filter(NativePokemon::isBoss)
                .sorted(NativePokemonComparator.of(bossPokemonOrder))
                .map(nativePokemon -> BiomeAllPokemonResponse.of(
                        nativePokemon,
                        getBiomePokemons(nativePokemon.getPokemonIds())))
                .toList();
    }

    private List<TrainerPokemonResponse> getTrainerPokemons(Biome biome) {
        return biome.getTrainers().stream()
                .map(trainer -> TrainerPokemonResponse.from(
                        trainer,
                        ImageUrl.getTrainerImage(trainer.getName()),
                        getTypesResponses(trainer.getTypes()),
                        getBiomePokemons(trainer.getPokemonIds()))
                )
                .toList();
    }

    private List<NextBiomeResponse> getNextBiomes(Biome biome) {
        return biome.getNextBiomes().stream()
                .map(nextBiomeInfo -> {
                    Biome nextBiome = biomeRepository.findById(nextBiomeInfo.getName())
                            .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));

                    return NextBiomeResponse.of(
                            nextBiome,
                            ImageUrl.getBiomeImage(nextBiome.getId()),
                            String.valueOf(nextBiomeInfo.getPercentage()),
                            getTypesResponses(nextBiome.getTypes()),
                            getTrainerTypesResponses(nextBiome.getTrainers())
                    );
                })
                .toList();
    }

    private List<BiomePokemonResponse> getBiomePokemons(List<String> biomePokemons) {
        List<BiomePokemonResponse> biomePokemonResponses = pokemonRepository.findAllById(biomePokemons).stream()
                .map(pokemon -> new BiomePokemonResponse(
                        pokemon.getId(),
                        pokemon.getKoName(),
                        ImageUrl.getPokemonImage(pokemon.getImageId()),
                        getTypesResponses(pokemon.getTypes()))
                )
                .distinct()
                .toList();
        if (biomePokemons.size() == biomePokemonResponses.size()) {
            return biomePokemonResponses;
        }

        throw new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND);
    }

    private List<BiomeTypeResponse> getTypesResponses(List<Type> types) {
        return types.stream()
                .map(type -> new BiomeTypeResponse(
                        ImageUrl.getTypeImage(type.getName()),
                        type.getKoName())
                )
                .toList();
    }

    private List<BiomeTypeResponse> getTrainerTypesResponses(List<Trainer> trainers) {
        return trainers.stream()
                .map(Trainer::getTypes)
                .flatMap(List::stream)
                .map(type -> new BiomeTypeResponse(
                        ImageUrl.getTypeImage(type.getName()),
                        type.getKoName())
                )
                .toList();
    }
}
