package com.pokerogue.helper.pokemon.service;


import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import com.pokerogue.helper.global.config.LanguageSetter;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EggMoveResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon.dto.PokemonBiomeResponse;
import com.pokerogue.helper.pokemon.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon.dto.PokemonMoveResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.PokemonInMemoryRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final MoveRepository moveRepository;
    private final BiomeRepository biomeRepository;
    private final AbilityRepository abilityRepository;
    private final EvolutionService evolutionService;
    private final PokemonInMemoryRepository pokemonInMemoryRepository;
    private final PokemonRepository pokemonRepository;

    public PokemonDetailResponse findById(String id) {
        Pokemon pokemon = pokemonRepository.findByIndexAndLanguage(id, LanguageSetter.getLanguage())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        return createPokemonDetailResponse(pokemon);
    }

    public List<PokemonResponse> findAll() {
        List<Pokemon> pokemons = pokemonInMemoryRepository.findAll();

        return pokemons.stream()
                .map(pokemon -> PokemonResponse.from(pokemon, createTypeResponse(pokemon)))
                .toList();
    }

    private PokemonDetailResponse createPokemonDetailResponse(Pokemon pokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = createTypeResponse(pokemon);
        List<PokemonAbilityResponse> pokemonAbilityResponses = createAbilityResponse(pokemon);
        List<PokemonMoveResponse> moveResponse = createMoveResponse(pokemon);
        List<EggMoveResponse> eggMoveResponses = createEggMoveResponse(pokemon);
        List<PokemonBiomeResponse> biomeResponses = createBiomeResponse(pokemon);
        EvolutionResponses evolutionResponses = evolutionService.getEvolutionResponses(pokemon);

        return PokemonDetailResponse.from(
                pokemon,
                pokemonTypeResponses,
                pokemonAbilityResponses,
                moveResponse,
                eggMoveResponses,
                biomeResponses,
                evolutionResponses
        );
    }

    private List<PokemonTypeResponse> createTypeResponse(Pokemon pokemon) {
        List<Type> types = pokemon.getTypes();

        return types.stream()
                .map(PokemonTypeResponse::from)
                .toList();
    }

    private List<PokemonAbilityResponse> createAbilityResponse(Pokemon pokemon) {
        List<String> abilityIds = new ArrayList<>(pokemon.getNormalAbilityIds());
        abilityIds.add(pokemon.getPassiveAbilityId());
        abilityIds.add(pokemon.getHiddenAbilityId());

        List<Optional<Ability>> abilities = abilityIds.stream()
                .map(ability -> abilityRepository.findByIndexAndLanguage(ability, LanguageSetter.getLanguage()))
                .toList();

        return PokemonAbilityResponse.createListFrom(abilities);
    }

    private List<PokemonBiomeResponse> createBiomeResponse(Pokemon pokemon) {
        List<String> biomes = pokemon.getBiomeIds();

        return biomes.stream()
                .map(biome -> biomeRepository.findByIndexAndLanguage(biome, LanguageSetter.getLanguage()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(PokemonBiomeResponse::from)
                .toList();
    }

    private List<EggMoveResponse> createEggMoveResponse(Pokemon pokemon) {
        List<String> moves = pokemon.getEggMoveIds();

        return moves.stream()
                .map(move -> moveRepository.findByIndexAndLanguage(move, LanguageSetter.getLanguage()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(EggMoveResponse::from)
                .toList();
    }

    private List<PokemonMoveResponse> createMoveResponse(Pokemon pokemon) {
        List<LevelMove> levelMoves = pokemon.getLevelMoves();

        return levelMoves.stream()
                .map(levelMove -> PokemonMoveResponse.from(getMoveById(levelMove), levelMove.getLevel()))
                .toList();
    }

    private Move getMoveById(LevelMove levelMove) {
        return moveRepository.findByIndexAndLanguage(levelMove.getMoveId(), LanguageSetter.getLanguage())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }
}
