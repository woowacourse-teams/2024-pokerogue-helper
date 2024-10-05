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
        String message = String.format("포켓몬의 총 개수가 다릅니다. 예상값: %d, 현재값: %d", POKEMON_SIZE, pokemonCount);

        validate(isTotalPokemonSizeMatch, pokemonCount, error, message);
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
            validate(isExpectedLetter, id, error, String.format(message, id));
            validate(isDelimiterSeparated, id, error2, String.format(message, id));
            validate(isDelimiterInPlace, id, error3, String.format(message, id));
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

        for (Pokemon pokemon : pokemons) {
            validate(isBaseTotalCorrect, pokemon, error);
        }
    }

    static void validatePokemonsGeneration(List<Pokemon> pokemons) {
        Predicate<Integer> isValidGeneration = gen -> gen >= MIN_GENERATION && gen <= MAX_GENERATION;
        ErrorMessage error = ErrorMessage.POKEMON_GENERATION_MISMATCH;

        for (Pokemon pokemon : pokemons) {
            int generation = pokemon.getGeneration();
            validate(isValidGeneration, generation, error);
        }
    }

    static void validatePokemonFormChanges(List<Pokemon> pokemons) {
        Predicate<Pokemon> isFormChangeable = pokemon -> !pokemon.getFormChanges().isEmpty();
        ErrorMessage error = ErrorMessage.POKEMON_FORM_CHANGE_MISMATCH;
        List<Pokemon> formChangeablePokemons = pokemons.stream()
                .filter(Pokemon::isCanChangeForm)
                .toList();

        for (Pokemon fomrChangeablePokemon : formChangeablePokemons) {
            validate(isFormChangeable, fomrChangeablePokemon, error);
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
        ErrorMessage error = ErrorMessage.POKEMON_RARITY_COUNT_MISMATCH;

        for (Pokemon pokemon : pokemons) {
            validate(isRarityCountLessOrEqualThanOne, pokemon, error);
        }
    }

    static void validateNormalAbilityCount(List<Pokemon> pokemons) {
        Predicate<Integer> isNormalAbilityCountInRange = normalAbilityCount ->
                isInRange(normalAbilityCount, MIN_NORMAL_ABILITY_COUNT, MAX_NORMAL_ABILITY_COUNT);
        ErrorMessage error = ErrorMessage.POKEMON_NORMAL_ABILITY_COUNT;

        for (Pokemon pokemon : pokemons) {
            int abilityCount = pokemon.getNormalAbilityIds().size();

            validate(isNormalAbilityCountInRange, abilityCount, error);
        }
    }

    static void validateAbilityDuplication(List<Pokemon> pokemons) {
        Predicate<Pokemon> isAbilityDisjointed = pokemon -> {
            List<String> totalAbilityIds = pokemon.getNormalAbilityIds();
            totalAbilityIds.add(pokemon.getHiddenAbilityId());
            totalAbilityIds.add(pokemon.getPassiveAbilityId());

            HashSet<String> uniqueIds = new HashSet<>(totalAbilityIds);

            return totalAbilityIds.size() == uniqueIds.size();
        };
        ErrorMessage error = ErrorMessage.POKEMON_NORMAL_ABILITY_COUNT;

        for (Pokemon pokemon : pokemons) {
            validate(isAbilityDisjointed, pokemon, error);
        }
    }

    static void throwIfNumberOutOfRange(List<Object> stats) {
        List<Double> numbers = stats.stream().map(Object::toString).map(Double::valueOf).toList();

        boolean validationFailed = numbers.stream().anyMatch(r -> r < 0 || r > 10000);

        if (validationFailed) {
            throw new IllegalArgumentException("numberOutOfRange");
        }
    }

    private static <T> void validate(Predicate<T> predicate, T data, ErrorMessage errorMessage) {
        if (predicate.test(data)) {
            return;
        }
        throw new GlobalCustomException(errorMessage, data.toString());
    }

    private static <T> void validate(Predicate<T> predicate, T data, ErrorMessage errorMessage,
                                     String detailedMessage) {
        if (predicate.test(data)) {
            return;
        }
        throw new GlobalCustomException(errorMessage, detailedMessage);
    }

    static boolean isDelimiter(int character) {
        return DELIMITER.charAt(0) == character;
    }
}
