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
    private static final IntPredicate isExpectedIdLetter = character -> isLowerCase(character)
                                                                        || isDigit(character)
                                                                        || isDelimiter(character);

    private PokemonValidator() {
    }

    static void validatePokemonSize(int pokemonCount) {
        Predicate<Integer> isTotalPokemonSizeMatch = count -> count == POKEMON_SIZE;
        ErrorMessage error = ErrorMessage.POKEMON_SIZE_MISMATCH;
        String message = String.format("포켓몬의 총 개수가 다릅니다: expected %d, but was %d", POKEMON_SIZE, pokemonCount);

        throwIf(isTotalPokemonSizeMatch, pokemonCount, error, message);
    }

    static void validatePokemonIdFormat(List<String> pokemonIds) {
        Predicate<String> isExpectedLetter = id -> id.codePoints().allMatch(isExpectedIdLetter);
        ErrorMessage error = ErrorMessage.POKEMON_ID_UNEXPECTED_LETTER;

        Predicate<String> isDelimiterSeparated = id -> id.contains(DELIMITER + DELIMITER);
        ErrorMessage error2 = ErrorMessage.POKEMON_ID_DELIMITER_IS_SEQUENTIAL;

        Predicate<String> isDelimiterInPlace = id -> id.startsWith(DELIMITER) || id.endsWith(DELIMITER);
        ErrorMessage error3 = ErrorMessage.POKEMON_ID_DELIMITER_PLACED_IN_EDGE;

        String message = "아이디 규칙에 맞지 않습니다. 아이디: %s";

        for (String id : pokemonIds) {
            throwIf(isExpectedLetter, id, error, String.format(message, id));
            throwIf(isDelimiterSeparated, id, error2, String.format(message, id));
            throwIf(isDelimiterInPlace, id, error3, String.format(message, id));
        }
    }

    static void validatePokemonsBaseTotal(List<Pokemon> pokemons) {
        Predicate<Pokemon> isBaseTotalCorrect = pokemon -> {
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
        ErrorMessage error = ErrorMessage.POKEMON_SIZE_MISMATCH;
        String message = "아이디가 %s인 포켓몬의 종족값이 실제 스탯의 합과 다릅니다.";

        for (Pokemon pokemon : pokemons) {
            throwIf(isBaseTotalCorrect, pokemon, error, message);
        }
    }

    static void validatePokemonsGeneration(List<Pokemon> pokemons) {
        Predicate<Integer> isValidGeneration = gen -> gen >= MIN_GENERATION && gen <= MAX_GENERATION;
        ErrorMessage error = ErrorMessage.POKEMON_GENERATION_MISMATCH;
        String message = "존재할 수 없는 포켓몬 세대입니다. 포켓몬 아이디: %s, 포켓몬 세대: %d";

        for (Pokemon pokemon : pokemons) {
            String pokemonId = pokemon.getId();
            int generation = pokemon.getGeneration();
            throwIf(isValidGeneration, generation, error, String.format(message, pokemonId, generation));
        }
    }

    static void validatePokemonFormChanges(List<Pokemon> pokemons) {
        List<Pokemon> formChangeablePokemons = pokemons.stream()
                .filter(Pokemon::isCanChangeForm)
                .toList();
        Predicate<Pokemon> isFormChangeable = pokemon -> !pokemon.getFormChanges().isEmpty();
        ErrorMessage error = ErrorMessage.POKEMON_FORM_CHANGE_MISMATCH;
        String message = "아이디가 %s인 포켓몬은 폼변환이 가능하지만, 변환 가능한 포켓몬이 존재하지 않습니다.";

        for (Pokemon fomrChangeablePokemon : formChangeablePokemons) {
            String id = fomrChangeablePokemon.getId();
            throwIf(isFormChangeable, fomrChangeablePokemon, error, String.format(message, id));
        }
    }

    public static void validatePokemonRarity(List<Pokemon> pokemons) {
        Predicate<Pokemon> isRarityCountLessOrEqualThanOne = pokemon -> {
            boolean legendary = pokemon.isLegendary();
            boolean mythical = pokemon.isMythical();
            boolean subLegendary = pokemon.isSubLegendary();

            long trueCount = Stream.of(legendary, mythical, subLegendary)
                    .filter(Boolean::booleanValue)
                    .count();

            return trueCount <= 1;
        };
        ErrorMessage error = ErrorMessage;
        String message = "";

        for (Pokemon pokemon : pokemons) {
            throwIf(isRarityCountLessOrEqualThanOne, pokemon, error, message);
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
