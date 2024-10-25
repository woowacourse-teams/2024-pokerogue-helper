package com.pokerogue.helper.biome.service;

import static com.pokerogue.helper.global.constant.SortingCriteria.ASCENDING;
import static com.pokerogue.helper.global.constant.SortingCriteria.DESCENDING;

import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.global.constant.SortingCriteria;
import java.util.Comparator;

public class NativePokemonComparator implements Comparator<NativePokemon> {

    private static final NativePokemonComparator ASCENDING_COMPARATOR = new NativePokemonComparator(ASCENDING);
    private static final NativePokemonComparator DESCENDING_COMPARATOR = new NativePokemonComparator(DESCENDING);

    private final SortingCriteria criteria;

    private NativePokemonComparator(SortingCriteria criteria) {
        this.criteria = criteria;
    }

    public static NativePokemonComparator of(SortingCriteria criteria) {
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
