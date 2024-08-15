package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final WeatherRepository weatherRepository;
    private final MoveRepository moveRepository;
    private final BattlePokemonRepository battlePokemonRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;

    public List<WeatherResponse> findWeathers() {
        return weatherRepository.findAll().stream()
                .map(WeatherResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(String pokemonId) {
        BattlePokemon battlePokemon = battlePokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        PokemonMovesByMachine pokemonMovesByMachine = pokemonMovesByMachineRepository.findByPokedexNumber(
                        battlePokemon.pokedexNumber())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MACHINE_MOVE_NOT_FOUND));

        List<String> moveIds = new ArrayList<>(battlePokemon.moveIds());
        moveIds.addAll(pokemonMovesByMachine.moveIds());
        List<Move> moves = moveIds.stream()
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
}
