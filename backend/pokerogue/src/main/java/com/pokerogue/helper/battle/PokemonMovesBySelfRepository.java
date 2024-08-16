package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonMovesBySelfRepository {

    private final Map<Integer, PokemonMovesBySelf> pokemonsMovesBySelf = new HashMap<>();

    public void save(PokemonMovesBySelf pokemonMovesBySelf) {
        pokemonsMovesBySelf.put(pokemonMovesBySelf.pokedexNumber(), pokemonMovesBySelf);
    }

    public List<PokemonMovesBySelf> findAll() {
        return pokemonsMovesBySelf.values()
                .stream()
                .toList();
    }
}
