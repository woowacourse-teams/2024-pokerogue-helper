package com.pokerogue.helper.pokemon.data;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

class PokemonValidator extends Validator {
    private static final int POKEMON_SIZE = 1446;
    private static final int MIN_GENERATION = 1;
    private static final int MAX_GENERATION = 9;
    private static final int MIN_TYPE_COUNT = 1;
    private static final int MAX_TYPE_COUNT = 2;
    private static final int MIN_ABILITY_COUNT = 2;
    private static final int MAX_ABILITY_COUNT = 4;
    private static final int MIN_NORMAL_ABILITY_COUNT = 1;
    private static final int MAX_NORMAL_ABILITY_COUNT = 2;
    private static final String DELIMITER = "_";
    private static final IntPredicate idCharacterRules = character -> isLowerCase(character)
                                                                      || isDigit(character)
                                                                      || isDelimiter(character);

    private PokemonValidator() {
    }

    static void validatePokemonSize(int size) {
        if (size == POKEMON_SIZE) {
            return;
        }
        throw new GlobalCustomException(ErrorMessage.POKEMON_SIZE_MISMATCH,
                String.format("expected %d, but was %d", POKEMON_SIZE, size));
    }

    static void validatePokemonIdFormat(List<Pokemon> pokemons) {
        List<String> ids = pokemons.stream().map(Pokemon::getId).toList();
        throwIfIdDuplicates(ids);

        ids.stream()
                .peek(PokemonValidator::throwIfCharacterNotAllowed)
                .peek(id -> throwIfDelimiterMisplaced(id, DELIMITER))
                .forEach(id -> throwIfDelimiterIsSequential(id, DELIMITER));
    }

    static void throwIfCharacterNotAllowed(String id) {
        if (id.codePoints().allMatch(idCharacterRules)) {
            return;
        }

        throw new GlobalCustomException(ErrorMessage.POKEMON_ID_LETTER_INVALID,
                String.format("expected follow id rule, but id was %s", id));
    }

    static void validatePokemonsBaseTotal(List<Pokemon> pokemons) {
        pokemons.forEach(PokemonValidator::throwIfTotalStatIncorrect);
    }

    static void throwIfTotalStatIncorrect(Pokemon pokemon) {
        int hp = pokemon.getHp();
        int attack = pokemon.getAttack();
        int specialAttack = pokemon.getSpecialAttack();
        int defense = pokemon.getDefense();
        int specialDefense = pokemon.getSpecialDefense();
        int speed = pokemon.getSpeed();

        int baseTotal = pokemon.getBaseTotal();
        int expectedBaseTotal = hp + attack + specialAttack + defense + specialDefense + speed;

        if (baseTotal == expectedBaseTotal) {
            return;
        }
        throw new IllegalArgumentException(pokemon.getId() + " 종족값이 일치하지 않습니다.");
    }

    static void validatePokemonsGeneration(List<Pokemon> pokemons) {
        List<Integer> generations = pokemons.stream()
                .map(Pokemon::getGeneration)
                .toList();

        generations.forEach(generation -> {
            Predicate<Integer> condition = gen -> gen >= MIN_GENERATION && gen <= MAX_GENERATION;
            GlobalCustomException exception = new GlobalCustomException(
                    ErrorMessage.POKEMON_GENERATION_MISMATCH,
                    "expected generation not in range: " + generation
            );
            throwIf(generation.getClass(), generation, condition, exception);
        });
    }

    private static <T> void throwIf(Class<?> clazz, T data, Predicate<T> predicate,
                                    GlobalCustomException exception) {
        if (predicate.test(data)) {
            return;
        }
        throw exception;
    }

    static void validatePokemonFormChanges(List<Pokemon> pokemons) {
        boolean validationFailed = pokemons.stream()
                .filter(Pokemon::isCanChangeForm)
                .map(Pokemon::getFormChanges)
                .anyMatch(List::isEmpty);

        if (validationFailed) {
            throw new IllegalArgumentException("Pokemon generation is invalid");
        }
    }

    public static void validatePokemonRarity(List<Pokemon> pokemons) {
        pokemons.forEach(PokemonValidator::throwIfRarityNotConsistent);
    }

    static void throwIfRarityNotConsistent(Pokemon pokemon) {
        boolean legendary = pokemon.isLegendary();
        boolean mythical = pokemon.isMythical();
        boolean subLegendary = pokemon.isSubLegendary();
        Boolean[] values = {legendary, mythical, subLegendary};

        long trueCount = Arrays.stream(values).filter(Boolean::booleanValue).count();

        if (trueCount > 1) {
            throw new IllegalArgumentException("rarityNotConsistence");
        }
    }

    public static void validateNormalAbilityCount(List<Pokemon> actual) {

    }


    static void throwIfNormalAbilityCountInvalid(int abilityCount) {
        if (isOutOfRange(abilityCount, MIN_NORMAL_ABILITY_COUNT, MAX_NORMAL_ABILITY_COUNT)) {
            throw new IllegalArgumentException("throwIfNormalAbilityCountInvalid");
        }
    }

    static void throwIfAbilityDuplicated(List<String> normalAbilityIds) {
        HashSet<String> strings = new HashSet<>(normalAbilityIds);
        if (strings.size() != normalAbilityIds.size()) {
            throw new IllegalArgumentException(normalAbilityIds + "throwIfAbilityDuplicated");
        }
    }

    static void throwIfNumberOutOfRange(List<Object> stats) {
        List<Double> numbers = stats.stream().map(Object::toString).map(Double::valueOf).toList();

        boolean validationFailed = numbers.stream().anyMatch(r -> r < 0 || r > 10000);

        if (validationFailed) {
            throw new IllegalArgumentException("numberOutOfRange");
        }
    }

    static boolean isDelimiter(int character) {
        return DELIMITER.charAt(0) == character;
    }
}
