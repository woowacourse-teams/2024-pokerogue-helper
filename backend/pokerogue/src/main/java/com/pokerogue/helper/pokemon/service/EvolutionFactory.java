package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;


@Getter
public class EvolutionFactory {

    private final List<Evolution> evolutions;
    private final Map<String, Integer> inDegrees;
    private final Map<String, Integer> depths;

    public EvolutionFactory(Pokemon pokemon) {
        evolutions = pokemon.getEvolutions();
        inDegrees = createInDegrees(evolutions);
        depths = createDepths();
    }


    public Map<String, Integer> createInDegrees(List<Evolution> evolutions) {
        return evolutions.stream()
                .flatMap(evolution -> Stream.of(Map.entry(evolution.getFrom(), 0), Map.entry(evolution.getTo(), 1)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Integer::sum));
    }

    private Map<String, Integer> createDepths() {
        Map<String, List<String>> edges = evolutions.stream()
                .collect(Collectors.groupingBy(Evolution::getFrom,
                        Collectors.mapping(Evolution::getTo, Collectors.toList())));

        TreeDepthCalculator treeDepthCalculator = new TreeDepthCalculator(inDegrees, edges);

        return Collections.unmodifiableMap(treeDepthCalculator.calculateDepths());
    }
}
