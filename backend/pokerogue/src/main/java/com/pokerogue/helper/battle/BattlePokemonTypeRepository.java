package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BattlePokemonTypeRepository {

    private final Map<String, PokemonType> pokemonTypes = new HashMap<>();

    public void save(PokemonType pokemonType) {
        pokemonTypes.put(pokemonType.name(), pokemonType);
    }

    public List<PokemonType> findAll() {
        return pokemonTypes.values()
                .stream()
                .toList();
    }

    public Optional<PokemonType> findByName(String name) {
        return Optional.ofNullable(pokemonTypes.get(name));
    }
}
