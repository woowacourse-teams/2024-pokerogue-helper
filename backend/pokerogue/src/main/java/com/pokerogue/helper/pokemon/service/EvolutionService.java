package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.dto.EvolutionResponse;
import com.pokerogue.helper.pokemon.dto.EvolutionResponses;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EvolutionService {

    private final PokemonRepository pokemonRepository;

    public EvolutionResponses getEvolutionResponses(Pokemon pokemon) {
        List<Evolution> evolutions = pokemon.getEvolutions();
        List<Pokemon> relatedPokemons = getRelatedPokemons(pokemon);
        Map<String, Integer> inorderMap = getInorderMap(evolutions);
        Map<String, Integer> depths = getDepths(inorderMap, evolutions);

        List<EvolutionResponse> responses = relatedPokemons.stream()
                .map(p -> EvolutionResponse.from(p, getEvolution(p, p.getEvolutions()), depths.get(p.getId())))
                .toList();

        return new EvolutionResponses(depths.get(pokemon.getId()), responses);
    }

    private Map<String, Integer> getInorderMap(List<Evolution> evolutions) {
        return evolutions.stream()
                .flatMap(evolution -> Stream.of(Map.entry(evolution.getFrom(), 0), Map.entry(evolution.getTo(), 1)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Integer::sum));
    }

    private List<Pokemon> getRelatedPokemons(Pokemon pokemon) {
        return pokemon.getEvolutions().stream()
                .flatMap(poke -> Stream.of(poke.getFrom(), poke.getTo()))
                .distinct()
                .map(pokemonRepository::findById)
                .filter(Optional::isPresent) // TODO: delete filter after data fix
                .map(Optional::get)
                .toList();
    }

    private Evolution getEvolution(Pokemon r, List<Evolution> evolutions) {
        return evolutions.stream()
                .filter(x -> x.getTo().equals(r.getId()))
                .findAny()
                .orElse(evolutions.stream()
                        .filter(x -> x.getFrom().equals(r.getId()))
                        .findAny()
                        .orElseThrow());
    }

    private record Result(List<Evolution> evolutions, List<Pokemon> relatedPokemons, Map<String, Integer> inorderMap,
                          Map<String, Integer> depths) {
    }

    private Map<String, Integer> getDepths(Map<String, Integer> inorders, List<Evolution> evolutions) {
        HashMap<String, Integer> inorder = new HashMap<>(inorders);
        Map<String, List<Evolution>> edges = evolutions.stream()
                .collect(Collectors.groupingBy(Evolution::getFrom, Collectors.toList()));

        Map<String, Integer> depths = new HashMap<>();

        Queue<String> q = new LinkedList<>();

        for (String key : inorder.keySet()) {
            if (inorder.get(key) == 0) {
                q.add(key);
            }
        }

        int depth = 0;
        while (!q.isEmpty()) {
            int qs = q.size();
            for (int i = 0; i < qs; i++) {
                String from = q.poll();
                depths.put(from, depth);
                if (edges.get(from) == null) {
                    continue;
                }
                for (Evolution evolution : edges.get(from)) {
                    inorder.put(evolution.getTo(), inorder.get(evolution.getTo()) - 1);
                    if (inorder.get(evolution.getTo()) == 0) {
                        q.add(evolution.getTo());
                    }
                }
            }
            depth++;
        }
        return Collections.unmodifiableMap(depths);
    }

}
