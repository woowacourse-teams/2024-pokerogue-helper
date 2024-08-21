package com.pokerogue.helper.pokemon2.repository;

import com.pokerogue.helper.pokemon2.data.Evolution;
import com.pokerogue.helper.pokemon2.data.EvolutionChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class EvolutionRepository {
    private final Map<String, List<Evolution>> data = new HashMap<>();
    private final Map<String, EvolutionChain> chains = new HashMap<>();

    public Map<String, List<Evolution>> findAll() {
        return Collections.unmodifiableMap(data);
    }

    public Optional<List<Evolution>> findEdgeById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    public Optional<EvolutionChain> findEvolutionChainById(String id) {
        return Optional.ofNullable(chains.get(id));
    }

    public void saveEdge(String key, Evolution value) {
        data.putIfAbsent(key, new ArrayList<>());
        data.get(key).add(value);
    }

    public void saveChain(String id, EvolutionChain value) {
        chains.put(id, value);
    }
}
