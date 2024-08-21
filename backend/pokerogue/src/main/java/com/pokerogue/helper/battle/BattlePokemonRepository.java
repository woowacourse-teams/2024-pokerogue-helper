package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BattlePokemonRepository {

    private final Map<String, BattlePokemon> battlePokemons = new HashMap<>();

    public void save(BattlePokemon battlePokemon) {
        battlePokemons.put(battlePokemon.id(), battlePokemon);
    }

    public List<BattlePokemon> findAll() {
        return battlePokemons.values()
                .stream()
                .toList();
    }

    public Optional<BattlePokemon> findById(String id) {
        return Optional.ofNullable(battlePokemons.get(id));
    }
}
