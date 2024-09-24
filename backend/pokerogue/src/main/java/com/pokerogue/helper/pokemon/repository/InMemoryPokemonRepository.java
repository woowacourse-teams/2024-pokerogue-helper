package com.pokerogue.helper.pokemon.repository;


import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPokemonRepository {
    private final Map<String, InMemoryPokemon> data = new TreeMap<>();

    public Map<String, InMemoryPokemon> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public Optional<InMemoryPokemon> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    public void save(String key, InMemoryPokemon inMemoryPokemon) {
        data.put(key, inMemoryPokemon);
    }
}
