package com.pokerogue.helper.pokemon2.repository;


import com.pokerogue.helper.pokemon2.data.Move;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRepository {
    private final Map<String, Move> data = new HashMap<>();

    public Optional<Move> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    public void save(String key, Move value) {
        data.put(key, value);
    }
}
