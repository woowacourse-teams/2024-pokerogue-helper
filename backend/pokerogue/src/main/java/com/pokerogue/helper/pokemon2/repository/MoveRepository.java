package com.pokerogue.helper.pokemon2.repository;


import com.pokerogue.helper.pokemon2.data.Move;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRepository {
    private final Map<String, Move> data = new TreeMap<>();

    public Move findById(String id) {
        return data.get(id);
    }

    public void save(String key, Move value) {
        data.put(key, value);
    }
}
