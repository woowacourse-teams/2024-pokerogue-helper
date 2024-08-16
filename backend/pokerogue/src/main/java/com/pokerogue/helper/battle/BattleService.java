package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private static final String TYPE_LOGO_URL_FORMAT = "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/%s-1.png";
    private static final String MOVE_CATEGORY_LOGO_URL_FORMAT = "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/%s.png";

    private final WeatherRepository weatherRepository;
    private final MoveRepository moveRepository;
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;
    private final PokemonTypeRepository pokemonTypeRepository;

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
                .map(this::toMoveResponseWithLogo)
                .toList();
    }

    private Move findMoveById(String id) {
        return moveRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }

    private MoveResponse toMoveResponseWithLogo(Move move) {
        System.out.println(move.type());
        String typeName = pokemonTypeRepository.findByKoName(move.type())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND)).getName();
        String typeLogo = String.format(TYPE_LOGO_URL_FORMAT, typeName);
        String categoryLogo = String.format(MOVE_CATEGORY_LOGO_URL_FORMAT, move.category().toLowerCase());
        return MoveResponse.of(move, typeLogo, categoryLogo);
    }
}
