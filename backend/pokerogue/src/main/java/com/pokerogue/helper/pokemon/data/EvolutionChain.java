package com.pokerogue.helper.pokemon.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class EvolutionChain {
    private List<List<String>> chain = new ArrayList<>();

    public EvolutionChain() {
    }

    public EvolutionChain(List<List<String>> newChain) {
        this.chain = newChain;
    }

    public void push(Evolution evolution, int depth) {
        while (chain.size() <= depth + 1) {
            chain.add(new ArrayList<>());
        }

        chain.get(depth).add(evolution.getFrom());
        chain.get(depth + 1).add(evolution.getTo());

        chain.set(depth, chain.get(depth).stream().distinct().collect(Collectors.toList()));
        chain.set(depth + 1, chain.get(depth + 1).stream().distinct().collect(Collectors.toList()));
    }

    public List<String> getAllIds() {
        return chain.stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public List<List<String>> getChain() {
        return chain;
    }
}
