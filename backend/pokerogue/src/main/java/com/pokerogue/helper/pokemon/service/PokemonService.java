package com.pokerogue.helper.pokemon.service;


import com.pokerogue.helper.ability.data.Ability;
import com.pokerogue.helper.ability.repository.InMemoryAbilityRepository;
import com.pokerogue.helper.battle.BattleMove;
import com.pokerogue.helper.battle.BattleMoveRepository;
import com.pokerogue.helper.biome.repository.InMemoryBiomeRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EggMoveResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.dto.MoveResponse;
import com.pokerogue.helper.pokemon.dto.PokemonAbilityResponse;
import com.pokerogue.helper.pokemon.dto.PokemonBiomeResponse;
import com.pokerogue.helper.pokemon.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final BattleMoveRepository battleMoveRepository;
    private final InMemoryBiomeRepository inMemoryBiomeRepository;
    private final InMemoryAbilityRepository inMemoryAbilityRepository;
    private final EvolutionService evolutionService;

    public PokemonDetailResponse findById(String id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        return createPokemonDetailResponse(pokemon);
    }

    public List<PokemonResponse> findAll() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        return pokemons.stream()
                .map(pokemon -> PokemonResponse.from(pokemon, createTypeResponse(pokemon)))
                .toList();
    }

    private PokemonDetailResponse createPokemonDetailResponse(Pokemon pokemon) {
        List<PokemonTypeResponse> pokemonTypeResponses = createTypeResponse(pokemon);
        List<PokemonAbilityResponse> pokemonAbilityResponses = createAbilityResponse(pokemon);
        List<MoveResponse> moveResponse = createMoveResponse(pokemon);
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
        List<String> abilityIds = pokemon.getNormalAbilityIds();
        abilityIds.add(pokemon.getPassiveAbilityId());
        abilityIds.add(pokemon.getHiddenAbilityId());

        List<Optional<Ability>> abilities = abilityIds.stream()
                .map(inMemoryAbilityRepository::findById)
                .toList();

        return PokemonAbilityResponse.createListFrom(abilities);
    }

    private List<PokemonBiomeResponse> createBiomeResponse(Pokemon pokemon) {
        List<String> biomes = pokemon.getBiomeIds();

        return biomes.stream()
                .map(inMemoryBiomeRepository::findById)
                .map(PokemonBiomeResponse::from)
                .toList();
    }

    private List<EggMoveResponse> createEggMoveResponse(Pokemon pokemon) {
        List<String> moves = pokemon.getEggMoveIds();

        return moves.stream()
                .map(battleMoveRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(EggMoveResponse::from)
                .toList();
    }

    private List<MoveResponse> createMoveResponse(Pokemon pokemon) {
        List<LevelMove> levelMoves = pokemon.getLevelMoves();

        return levelMoves.stream()
                .map(levelMove -> MoveResponse.from(levelMove, getMoveById(levelMove)))
                .toList();
    }

    private BattleMove getMoveById(LevelMove levelMove) {
        return battleMoveRepository.findById(levelMove.getMoveId())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }
}
