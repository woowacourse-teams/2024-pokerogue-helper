package com.pokerogue.helper.move.service;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
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

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        List<Pokemon> pokemons = pokemonRepository.findByPokedexNumber(pokedexNumber);
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

    public MoveResponse findMove(String id) {
        Move move = findMoveById(id);
        return MoveResponse.from(move);
    }

    private Move findMoveById(String id) {
        return moveRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }
}
