package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonMovesByMachineRepository {

    private final Map<Integer, PokemonMovesByMachine> pokemonsMovesByMachine = new HashMap<>();

    public void save(PokemonMovesByMachine pokemonMovesByMachine) {
        pokemonsMovesByMachine.put(pokemonMovesByMachine.pokedexNumber(), pokemonMovesByMachine);
    }

    public List<PokemonMovesByMachine> findAll() {
        return pokemonsMovesByMachine.values()
                .stream()
                .toList();
    }

    public Optional<PokemonMovesByMachine> findByPokedexNumber(Integer pokedexNumber) {
        return Optional.ofNullable(pokemonsMovesByMachine.get(pokedexNumber));
    }
}
