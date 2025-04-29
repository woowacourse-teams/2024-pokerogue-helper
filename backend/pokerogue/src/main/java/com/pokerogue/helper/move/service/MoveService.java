package com.pokerogue.helper.move.service;

import com.pokerogue.helper.global.config.LanguageSetter;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.dto.MoveDetailResponse;
import com.pokerogue.helper.move.dto.MoveResponse;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveService {

    private final PokemonRepository pokemonRepository;
    private final MoveRepository moveRepository;

    public List<MoveResponse> findMoves() {
        return moveRepository.findAll().stream()
                .filter(move -> move.hasSameLanguage(LanguageSetter.getLanguage()))
                .map(MoveResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        List<Pokemon> pokemons = pokemonRepository.findByPokedexNumberAndLanguage(pokedexNumber, LanguageSetter.getLanguage());
        if (pokemons.isEmpty()) {
            throw new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND);
        }
        return makeMoveResponse(pokemons.get(0));
    }

    private List<MoveResponse> makeMoveResponse(Pokemon pokemon) {
        List<String> allMoveIds = getAllMoveIds(pokemon);
        List<Move> moves = allMoveIds.stream()
                .distinct()
                .map(this::findMoveById)
                .toList();

        return moves.stream()
                .map(MoveResponse::from)
                .toList();
    }

    private static List<String> getAllMoveIds(Pokemon pokemon) {
        List<String> allMoveIds = new ArrayList<>();
        List<String> levelMoves = pokemon.getLevelMoves().stream()
                .map(LevelMove::getMoveId)
                .toList();
        allMoveIds.addAll(levelMoves);
        allMoveIds.addAll(pokemon.getTechnicalMachineMoveIds());
        allMoveIds.addAll(pokemon.getEggMoveIds());
        return allMoveIds;
    }

    public MoveResponse findMoveInBattle(String id) {
        Move move = findMoveById(id);
        return MoveResponse.from(move);
    }

    public MoveDetailResponse findMove(String id) {
        Move move = findMoveById(id);
        List<String> eggMovePokemonIds = pokemonRepository.findByEggMoveIdsContains(move.getId()).stream()
                .filter(pokemon -> pokemon.hasSameLanguage(LanguageSetter.getLanguage()))
                .map(Pokemon::getId)
                .toList();
        List<String> levelMovePokemonIds = pokemonRepository.findByLevelMovesMoveId(move.getId()).stream()
                .filter(pokemon -> pokemon.hasSameLanguage(LanguageSetter.getLanguage()))
                .map(Pokemon::getId)
                .toList();

        return MoveDetailResponse.from(move, levelMovePokemonIds, eggMovePokemonIds);
    }

    private Move findMoveById(String id) {
        return moveRepository.findByIdAndLanguage(id, LanguageSetter.getLanguage())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }
}
