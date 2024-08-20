package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonMovesByEggRepository {

    private final Map<Integer, PokemonMovesByEgg> pokemonsMovesByEgg = new HashMap<>();

    public void save(PokemonMovesByEgg pokemonMovesByEgg) {
        pokemonsMovesByEgg.put(pokemonMovesByEgg.pokedexNumber(), pokemonMovesByEgg);
    }

    public List<PokemonMovesByEgg> findAll() {
        return pokemonsMovesByEgg.values()
                .stream()
                .toList();
    }

    public Optional<PokemonMovesByEgg> findByPokedexNumber(Integer pokedexNumber) {
        return Optional.ofNullable(pokemonsMovesByEgg.get(pokedexNumber));
    }
}
