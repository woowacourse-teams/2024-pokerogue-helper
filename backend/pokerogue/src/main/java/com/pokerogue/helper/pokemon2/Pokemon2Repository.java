package com.pokerogue.helper.pokemon2;


import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

@Component
public class Pokemon2Repository {
    private final Map<String, Map<String, String>> data = new TreeMap<>();

    public Map<String, Map<String, String>> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public void save(String key, Map<String, String> value) {
        data.put(key, value);
    }
}
