package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Evolution;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EvolutionContext {

    private final List<Evolution> evolutions;
    private final Map<String, List<String>> edges;
    private final Map<String, Integer> depth;

    public EvolutionContext(List<Evolution> evolutions) {
        this.evolutions = evolutions;
        edges = createEdges(evolutions);
        depth = new TreeDepthCalculator(edges).calculateDepths();
    }

    private Map<String, List<String>> createEdges(List<Evolution> evolutions) {
        return evolutions.stream().collect(Collectors.groupingBy(Evolution::getFrom,
                Collectors.mapping(Evolution::getTo, Collectors.toList())));
    }

    public Evolution getEvolutionOf(String pokemonId) {
        return Stream.of(
                        evolutions.stream().filter(evolution -> evolution.getTo().equals(pokemonId)),
                        evolutions.stream().filter(evolution -> evolution.getFrom().equals(pokemonId))
                )
                .flatMap(stream -> stream)
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
    }

    public Integer getDepthOf(String pokemonId) {
        return depth.getOrDefault(pokemonId, -1); // TODO: default value, some pokemon dont have evolutions
    }
}
