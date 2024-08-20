package com.pokerogue.helper.pokemon2.repository;


import com.pokerogue.helper.pokemon2.data.Pokemon;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Repository;

@Repository
public class Pokemon2Repository {
    private final Map<String, Pokemon> data = new TreeMap<>();

    public Map<String, Pokemon> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public Pokemon findById(String id) {
        return data.get(id);
    }

    public void save(String key, Pokemon pokemon) {
        data.put(key, pokemon);
    }
}
