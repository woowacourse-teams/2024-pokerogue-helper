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
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;

    public List<WeatherResponse> findWeathers() {
        return weatherRepository.findAll().stream()
                .map(WeatherResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        List<String> allMoveIds = new ArrayList<>();
        PokemonMovesBySelf pokemonMovesBySelf = pokemonMovesBySelfRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_SELF_NOT_FOUND));
        PokemonMovesByMachine pokemonMovesByMachine = pokemonMovesByMachineRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_MACHINE_NOT_FOUND));
        PokemonMovesByEgg pokemonMovesByEgg = pokemonMovesByEggRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_EGG_NOT_FOUND));

        allMoveIds.addAll(pokemonMovesBySelf.moveIds());
        allMoveIds.addAll(pokemonMovesByMachine.moveIds());
        allMoveIds.addAll(pokemonMovesByEgg.moveIds());
        List<Move> moves = allMoveIds.stream()
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
