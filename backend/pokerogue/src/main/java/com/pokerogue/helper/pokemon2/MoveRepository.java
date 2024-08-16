package com.pokerogue.helper.pokemon2;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRepository {
    private final Map<String, Map<String, String>> data = new TreeMap<>();

    public Map<String, Map<String, String>> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public void save(String key, Map<String, String> value) {
        data.put(key, value);
    }
}
