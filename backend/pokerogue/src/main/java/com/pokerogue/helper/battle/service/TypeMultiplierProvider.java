package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.collection.TypeMatching;
import com.pokerogue.helper.type.data.Type;
import com.pokerogue.helper.type.repository.TypeMatchingRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeMultiplierProvider {

    private static final double STRONG_TYPE_MATCHING_RESULT = 2;

    private final TypeMatchingRepository typeMatchingRepository;

    public List<BattleMultiplier> getAllByTypeMatchings(Type fromType, List<Type> toTypes) {
        return toTypes.stream()
                .map(toType -> findTypeMatchingByFromAndTo(fromType, toType))
                .map(TypeMatching::getResult)
                .map(BigDecimal::valueOf)
                .map(BattleMultiplier::valueOf)
                .toList();
    }

    public BattleMultiplier getBySameTypeAttackBonus(Type moveType, Pokemon rivalPokemon) {
        if (rivalPokemon.hasSameType(moveType)) {
            return BattleMultiplier.STRONG_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    public BattleMultiplier getByStrongWind(Type moveType, List<Type> rivalPokemonTypes) {
        if (isStrongWindConditionMet(moveType, rivalPokemonTypes)) {
            return BattleMultiplier.WEAK_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    private boolean isStrongWindConditionMet(Type moveType, List<Type> rivalPokemonTypes) {
        TypeMatching typeMatching = findTypeMatchingByFromAndTo(moveType, Type.FLYING);

        return rivalPokemonTypes.contains(Type.FLYING)
                && typeMatching.getResult() == STRONG_TYPE_MATCHING_RESULT;
    }

    private TypeMatching findTypeMatchingByFromAndTo(Type from, Type to) {
        return typeMatchingRepository.findByFromAndTo(from.getName(), to.getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));
    }
}
