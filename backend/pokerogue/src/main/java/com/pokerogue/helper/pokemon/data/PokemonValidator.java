package com.pokerogue.helper.pokemon.data;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.type.data.Type;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.logging.log4j.util.Strings;

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
    private static final int MIN_STAT_VALUE = -1;
    private static final int MAX_STAT_VALUE = 10000;
    private static final String DELIMITER = "_";
    private static final String EMPTY_ABILITY_DEFAULT_VALUE = "none";
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

    static void validateTotalAbilityCount(List<Pokemon> pokemons) {
        Predicate<Pokemon> isTotalAbilityCountInRange = pokemon -> {
            List<String> totalAbilityIds = pokemon.getNormalAbilityIds();
            if (!EMPTY_ABILITY_DEFAULT_VALUE.equals(pokemon.getHiddenAbilityId())) {
                totalAbilityIds.add(pokemon.getHiddenAbilityId());
            }
            totalAbilityIds.add(pokemon.getPassiveAbilityId());
            int totalCount = totalAbilityIds.size();

            return isInRange(totalCount, MIN_ABILITY_COUNT, MAX_ABILITY_COUNT);
        };
        ErrorMessage error = ErrorMessage.POKEMON_NORMAL_ABILITY_COUNT;

        for (Pokemon pokemon : pokemons) {
            validate(isTotalAbilityCountInRange, pokemon, error);
        }
    }

    static void validateTotalAbilityDuplication(List<Pokemon> pokemons) {
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

    static void validateStatValueRange(List<Pokemon> pokemons) {
        Predicate<Pokemon> isStatsInRange = pokemon -> {
            List<Number> stats = List.of(
                    pokemon.getDefense(),
                    pokemon.getAttack(),
                    pokemon.getSpecialAttack(),
                    pokemon.getSpecialDefense(),
                    pokemon.getWeight(),
                    pokemon.getHeight(),
                    pokemon.getFriendship(),
                    pokemon.getBaseExp(),
                    pokemon.getBaseTotal(),
                    pokemon.getHp(),
                    pokemon.getSpeed()
            );

            return stats.stream()
                    .map(Number::doubleValue)
                    .map(Double::intValue)
                    .allMatch(statValue -> isInRange(statValue, MIN_STAT_VALUE, MAX_STAT_VALUE));
        };

        for (Pokemon pokemon : pokemons) {
            validate(isStatsInRange, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH);
        }
    }

    static void validatePassiveAbilityExist(List<Pokemon> pokemons) {
        Predicate<Pokemon> isPassiveExist = pokemon -> {
            String passiveAbilityId = pokemon.getPassiveAbilityId();
            return Strings.isNotBlank(passiveAbilityId) && !EMPTY_ABILITY_DEFAULT_VALUE.equals(passiveAbilityId);
        };

        for (Pokemon pokemon : pokemons) {
            validate(isPassiveExist, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH);
        }
    }

    static void validateEmptyHiddenAbilityExists(List<Pokemon> pokemons) {
        Predicate<Pokemon> isEmptyHiddenExist = pokemon -> {
            String hiddenAbilityId = pokemon.getPassiveAbilityId();
            return Strings.isNotBlank(hiddenAbilityId) || EMPTY_ABILITY_DEFAULT_VALUE.equals(hiddenAbilityId);
        };

        boolean isEmptyExist = pokemons.stream().anyMatch(isEmptyHiddenExist);

        validate(Predicate.isEqual(isEmptyExist), isEmptyExist, ErrorMessage.POKEMON_SIZE_MISMATCH);
    }

    static void validateTypeCount(List<Pokemon> pokemons) {
        Predicate<Pokemon> isTypeCountInRange = pokemon -> {
            int typeCount = pokemon.getTypes().size();
            return isInRange(typeCount, MIN_TYPE_COUNT, MAX_TYPE_COUNT);
        };

        for (Pokemon pokemon : pokemons) {
            validate(isTypeCountInRange, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH);
        }
    }

    public static void validateTypeDuplication(List<Pokemon> pokemons) {
        Predicate<Pokemon> isTypeDisjointed = pokemon -> {
            List<Type> types = pokemon.getTypes();
            HashSet<Type> uniqueTypes = new HashSet<>(types);
            return types.size() == uniqueTypes.size();
        };

        for (Pokemon pokemon : pokemons) {
            validate(isTypeDisjointed, pokemon, ErrorMessage.POKEMON_SIZE_MISMATCH);
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

    private static boolean isDelimiter(int character) {
        return DELIMITER.charAt(0) == character;
    }
}
