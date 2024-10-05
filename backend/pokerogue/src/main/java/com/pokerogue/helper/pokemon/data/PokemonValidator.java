package com.pokerogue.helper.pokemon.data;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    static void validatePokemonSize(int pokemonCount) {
        Predicate<Integer> condition = count -> count == POKEMON_SIZE;
        String detailedMessage = String.format("expected %d, but was %d", POKEMON_SIZE, pokemonCount);
        throwIf(condition, pokemonCount, ErrorMessage.POKEMON_SIZE_MISMATCH, detailedMessage);
    }

    static void validatePokemonIdFormat(List<Pokemon> pokemons) {
        List<String> pokemonIds = pokemons.stream()
                .map(Pokemon::getId)
                .toList();
        Predicate<String> isExpectedLetter = id -> id.codePoints().allMatch(idCharacterRules);
        Predicate<String> isDelimiterNotSequential = id -> id.contains(DELIMITER + DELIMITER);
        Predicate<String> isDelimiterNotInEdge = id -> id.startsWith(DELIMITER) || id.endsWith(DELIMITER);
        String message = "illegal id was %s";

        for (String id : pokemonIds) {
            throwIf(isExpectedLetter, id, ErrorMessage.POKEMON_ID_UNEXPECTED_LETTER, String.format(message, id));
            throwIf(isDelimiterNotSequential, id, ErrorMessage.POKEMON_ID_DELIMITER_IS_SEQUENTIAL,
                    String.format(message, id));
            throwIf(isDelimiterNotInEdge, id, ErrorMessage.POKEMON_ID_DELIMITER_PLACED_IN_EDGE,
                    String.format(message, id));
        }
    }

    static void validatePokemonsBaseTotal(List<Pokemon> pokemons) {
        Predicate<Pokemon> condition = pokemon -> {
            int hp = pokemon.getHp();
            int attack = pokemon.getAttack();
            int specialAttack = pokemon.getSpecialAttack();
            int defense = pokemon.getDefense();
            int specialDefense = pokemon.getSpecialDefense();
            int speed = pokemon.getSpeed();

            int baseTotal = pokemon.getBaseTotal();
            int summation = hp + attack + specialAttack + defense + specialDefense + speed;

            return baseTotal == summation;
        };
        String detailedMessage = "아이디가 %s인 포켓몬의 종족값이 실제 스탯의 합과 다릅니다.";

        for (Pokemon pokemon : pokemons) {
            throwIf(condition, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH,
                    String.format(detailedMessage, pokemon.getId()));
        }
    }

    static void validatePokemonsGeneration(List<Pokemon> pokemons) {
        Predicate<Integer> condition = gen -> gen >= MIN_GENERATION && gen <= MAX_GENERATION;
        List<Integer> generations = pokemons.stream()
                .map(Pokemon::getGeneration)
                .toList();
        String message = "expected generation not in range: %s";

        for (Integer generation : generations) {
            throwIf(condition, generation, ErrorMessage.POKEMON_GENERATION_MISMATCH, String.format(message));
        }
    }

    static void validatePokemonFormChanges(List<Pokemon> pokemons) {
        Predicate<Pokemon> condition = pokemon -> pokemon.getFormChanges().isEmpty();
        String message = "";
        List<Pokemon> formChangeablePokemons = pokemons.stream()
                .filter(Pokemon::isCanChangeForm)
                .toList();

        for (Pokemon fomrChangeablePokemon : formChangeablePokemons) {
            throwIf(condition, fomrChangeablePokemon, ErrorMessage.POKEMON_SIZE_MISMATCH, String.format(message));
        }
    }

    public static void validatePokemonRarity(List<Pokemon> pokemons) {
        Predicate<Pokemon> condition = pokemon -> {
            boolean legendary = pokemon.isLegendary();
            boolean mythical = pokemon.isMythical();
            boolean subLegendary = pokemon.isSubLegendary();

            long trueCount = Stream.of(legendary, mythical, subLegendary)
                    .filter(Boolean::booleanValue)
                    .count();

            return trueCount <= 1;
        };
        String message = "";

        for (Pokemon pokemon : pokemons) {
            throwIf(condition, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH, message);
        }
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

    static <T> void throwIf(Predicate<T> predicate, T data, ErrorMessage errorMessage, String detailedMessage) {
        if (predicate.test(data)) {
            return;
        }
        throw new GlobalCustomException(errorMessage, detailedMessage);
    }

    static boolean isDelimiter(int character) {
        return DELIMITER.charAt(0) == character;
    }
}
