package com.pokerogue.helper.biome.service;

import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.biome.dto.BiomeAllPokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeDetailResponse;
import com.pokerogue.helper.biome.dto.BiomePokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.dto.BiomeTypeResponse;
import com.pokerogue.helper.biome.dto.NextBiomeResponse;
import com.pokerogue.helper.biome.dto.TrainerPokemonResponse;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiomeService {

    private final S3Service s3Service;
    private final BiomeRepository biomeRepository;
    private final PokemonRepository pokemonRepository;

    public List<BiomeResponse> findBiomes() {
        return biomeRepository.findAll().stream()
                .map(biome -> BiomeResponse.of(
                        biome,
                        s3Service.getBiomeImageFromS3(biome.getId()),
                        getTypesResponses(biome.getTypes()),
                        getTrainerTypesResponses(biome.getTrainers()))
                )
                .toList();
    }

    private List<BiomeTypeResponse> getTypesResponses(List<String> types) {
        return types.stream()
                .map(type -> new BiomeTypeResponse(
                        s3Service.getPokerogueTypeImageFromS3(type),
                        BiomePokemonType.getTypeNameById(type)))
                .toList();
    }

    private List<BiomeTypeResponse> getTrainerTypesResponses(List<Trainer> trainers) {
        return trainers.stream()
                .map(Trainer::getTypes)
                .flatMap(List::stream)
                .map(type -> new BiomeTypeResponse(
                        s3Service.getPokerogueTypeImageFromS3(type),
                        BiomePokemonType.getTypeNameById(type)))
                .toList();
    }

    public BiomeDetailResponse findBiome(String id) {
        Biome biome = biomeRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.BIOME_NOT_FOUND));

        return BiomeDetailResponse.of(
                biome,
                s3Service.getBiomeImageFromS3(biome.getId()),
                getWildPokemons(biome.getNativePokemons()),
                getBossPokemons(biome.getNativePokemons()),
                getTrainerPokemons(biome),
                getNextBiomes(biome)
        );
    }

    private List<BiomeAllPokemonResponse> getWildPokemons(List<NativePokemon> nativePokemons) {
        return nativePokemons.stream()
                .filter(NativePokemon::isWild)
                .map(nativePokemon -> BiomeAllPokemonResponse.of(
                        nativePokemon,
                        getBiomePokemons(nativePokemon.getPokemonIds())))
                .toList();
    }

    private List<BiomePokemonResponse> getBiomePokemons(List<String> biomePokemons) {
        return biomePokemons.stream()
                .map(biomePokemon -> pokemonRepository.findById(biomePokemon)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND))
                )
                .map(pokemon -> new BiomePokemonResponse(
                        pokemon.getId(),
                        pokemon.getKoName(),
                        s3Service.getPokemonImageFromS3(pokemon.getImageId()),
                        getTypesResponses(pokemon.getTypes()))
                )
                .distinct()
                .toList();
    }

    private List<BiomeAllPokemonResponse> getBossPokemons(List<NativePokemon> nativePokemons) {
        return nativePokemons.stream()
                .filter(NativePokemon::isBoss)
                .map(nativePokemon -> BiomeAllPokemonResponse.of(
                        nativePokemon,
                        getBiomePokemons(nativePokemon.getPokemonIds())))
                .toList();
    }

    private List<TrainerPokemonResponse> getTrainerPokemons(Biome biome) {
        return biome.getTrainers().stream()
                .map(trainer -> TrainerPokemonResponse.from(
                        trainer,
                        s3Service.getTrainerImageFromS3(trainer.getName()),
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
                            s3Service.getBiomeImageFromS3(nextBiome.getId()),
                            String.valueOf(nextBiomeInfo.getPercentage()),
                            getTypesResponses(nextBiome.getTypes()),
                            getTrainerTypesResponses(nextBiome.getTrainers())
                    );
                })
                .toList();
    }
}
