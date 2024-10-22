package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.EvolutionChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class EvolutionRepository {
    private final Map<String, List<Evolution>> edges = new HashMap<>();
    private final Map<String, EvolutionChain> chains = new HashMap<>();

    public Map<String, List<Evolution>> findAll() {
        return Collections.unmodifiableMap(edges);
    }

    public Optional<List<Evolution>> findEdgeById(String id) {
        return Optional.ofNullable(edges.get(id));
    }

    public Optional<EvolutionChain> findEvolutionChainById(String id) {
        return Optional.ofNullable(chains.get(id));
    }

    public void saveEdge(String key, Evolution value) {
        edges.putIfAbsent(key, new ArrayList<>());
        edges.get(key).add(value);
    }

    public void saveChain(String id, EvolutionChain value) {
        chains.put(id, value);
    }
}
