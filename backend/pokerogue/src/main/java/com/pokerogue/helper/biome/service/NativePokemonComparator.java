package com.pokerogue.helper.biome.service;

import com.pokerogue.helper.biome.data.NativePokemon;
import java.util.Comparator;

public class NativePokemonComparator implements Comparator<NativePokemon> {

    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "desc";
    private static final NativePokemonComparator ASCENDING_COMPARATOR = new NativePokemonComparator(ASCENDING);
    private static final NativePokemonComparator DESCENDING_COMPARATOR = new NativePokemonComparator(DESCENDING);

    private final String criteria;

    private NativePokemonComparator(String criteria) {
        this.criteria = criteria;
    }

    public static NativePokemonComparator of(String criteria) {
        if (criteria.equals(ASCENDING)) {
            return ASCENDING_COMPARATOR;
        }
        return DESCENDING_COMPARATOR;
    }

    @Override
    public int compare(NativePokemon firstPokemon, NativePokemon secondPokemon) {
        if (this.criteria.equals(ASCENDING)) {
            return Integer.compare(firstPokemon.getRarity(), secondPokemon.getRarity());
        }

        return Integer.compare(secondPokemon.getRarity(), firstPokemon.getRarity());
    }
}
