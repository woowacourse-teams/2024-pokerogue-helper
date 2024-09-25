package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.TypeMatching;
import com.pokerogue.helper.battle.repository.TypeMatchingRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeMultiplier extends BattleMultiplier {

    private static final double STRONG_TYPE_MATCHING_RESULT = 2;

    private final TypeMatchingRepository typeMatchingRepository;

    public double getByTypeMatching(Type attackMoveType, List<Type> rivalPokemonTypes) {
        return rivalPokemonTypes.stream()
                .map(toType -> findTypeMatchingByFromAndTo(attackMoveType, toType))
                .map(TypeMatching::getResult)
                .reduce(DEFAULT_MULTIPLIER, this::multiply);
    }

    private double multiply(double a, double b) {
        return a * b;
    }

    public double getBySameTypeAttackBonus(Type moveType, Pokemon rivalPokemon) {
        if (rivalPokemon.hasSameType(moveType)) {
            return STRONG_MULTIPLIER;
        }

        return DEFAULT_MULTIPLIER;
    }

    public double getByStrongWind(Type moveType, List<Type> rivalPokemonTypes) {
        TypeMatching typeMatching = findTypeMatchingByFromAndTo(moveType, Type.FLYING);
        if (rivalPokemonTypes.contains(Type.FLYING)
                && typeMatching.getResult() == STRONG_TYPE_MATCHING_RESULT) {
            return WEAK_MULTIPLIER;
        }

        return DEFAULT_MULTIPLIER;
    }

    private TypeMatching findTypeMatchingByFromAndTo(Type from, Type to) {
        return typeMatchingRepository.findByFromAndTo(from.getName(), to.getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));
    }
}
