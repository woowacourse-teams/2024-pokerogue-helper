package com.pokerogue.helper.pokemon.repository;


import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonRepository {
    private final Map<String, Pokemon> data = new TreeMap<>();

    public Map<String, Pokemon> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public Optional<Pokemon> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    public void save(String key, Pokemon pokemon) {
        data.put(key, pokemon);
    }
}
