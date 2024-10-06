package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EvolutionContext {

    private final List<Evolution> evolutions;
    private final Map<String,List<String>> edges;
    private final Map<String, Integer> depths;

    public EvolutionContext(List<Evolution> evolutions) {
        this.evolutions = evolutions;
        edges = evolutions.stream()
                .collect(Collectors.groupingBy(Evolution::getFrom, Collectors.mapping(Evolution::getTo,
                        Collectors.toList())));
        depths = new TreeDepthCalculator(edges).calculateDepths();
    }

    public Evolution findEvolution(String pokemonId) {
        return Stream.of(
                        evolutions.stream().filter(evolution -> evolution.getTo().equals(pokemonId)),
                        evolutions.stream().filter(evolution -> evolution.getFrom().equals(pokemonId))
                )
                .flatMap(stream -> stream)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Evolution not found for Pokémon: " + pokemonId));
    }

    public Integer getDepthOf(String pokemonId) {
        return depths.get(pokemonId);
    }

}
