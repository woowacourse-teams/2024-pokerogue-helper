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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveService {

    private final PokemonRepository pokemonRepository;
    private final MoveRepository moveRepository;

    private Map<Integer, List<MoveResponse>> findByDexnumberCache = new HashMap<>();

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        if (findByDexnumberCache.isEmpty()) {
            initFindByDexnumberCache();
        }

        return findByDexnumberCache.get(pokedexNumber);
    }

    private void initFindByDexnumberCache() {
        for (Pokemon pokemon : pokemonRepository.findAll()) {
            int pokedexNumber = pokemon.getPokedexNumber();
            if (!findByDexnumberCache.containsKey(pokedexNumber)) {
                findByDexnumberCache.put(pokedexNumber, makeMoveResponse(pokemon));
            }
        }
    }

    private List<MoveResponse> makeMoveResponse(Pokemon pokemon) {
        List<String> allMoveIds = new ArrayList<>();
        List<String> levelMoves = pokemon.getLevelMoves().stream()
                .map(LevelMove::getMoveId)
                .toList();
        allMoveIds.addAll(levelMoves);
        allMoveIds.addAll(pokemon.getTechnicalMachineMoveIds());
        allMoveIds.addAll(pokemon.getEggMoveIds());
        List<Move> moves = allMoveIds.stream()
                .distinct()
                .map(this::findMoveById)
                .toList();

        return moves.stream()
                .map(MoveResponse::from)
                .toList();
    }

    private Move findMoveById(String id) {
        return moveRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }

    public MoveResponse findMove(String id) {
        return MoveResponse.from(findMoveById(id));
    }
}
