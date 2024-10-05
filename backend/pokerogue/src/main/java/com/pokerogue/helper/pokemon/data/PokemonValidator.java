package com.pokerogue.helper.pokemon.data;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

class PokemonValidator extends Validator {
    private static final int POKEMON_SIZE = 1446;
    private static final int MIN_GENERATION = 1;
    private static final int MAX_GENERATION = 9;
    private static final String DELIMITER = "_";
    private static final IntPredicate idCharacterRules = character -> isLowerCase(character)
                                                                      || isDigit(character)
                                                                      || isDelimiter(character);

    private PokemonValidator() {
    }

    static void validatePokemonSize(int size) {
        if (size != POKEMON_SIZE) {
            throw new IllegalArgumentException("Pokemon size is not equal to " + POKEMON_SIZE);
        }
    }

    static void validatePokemonIdFormat(List<Pokemon> pokemons) {
        throwIfNull(pokemons);
        List<String> ids = pokemons.stream().map(Pokemon::getId).toList();
        throwIfIdDuplicates(ids);

        ids.stream().peek(PokemonValidator::throwIfCharacterNotAllowed)
                .peek(id -> throwIfDelimiterMisplaced(id, DELIMITER))
                .forEach(id -> throwIfDelimiterIsSequential(id, DELIMITER));
    }

    static void throwIfCharacterNotAllowed(String id) {
        boolean validationFailed = !id.codePoints().allMatch(idCharacterRules);

        if (validationFailed) {
            throw new IllegalArgumentException("Invalid character '" + id + "'");
        }
    }

    static boolean isDelimiter(int character) {
        return DELIMITER.charAt(0) == character;
    }

    static void validatePokemonTotalState(List<Pokemon> pokemons) {
        pokemons.forEach(PokemonValidator::throwIfTotalStatIncorrect);
    }

    static void throwIfTotalStatIncorrect(Pokemon pokemon) {
        int hp = pokemon.getHp();
        int attack = pokemon.getAttack();
        int specialAttack = pokemon.getSpecialAttack();
        int defense = pokemon.getDefense();
        int specialDefense = pokemon.getSpecialDefense();
        int speed = pokemon.getSpeed();

        int actualTotalStat = pokemon.getBaseTotal();
        int expectedTotalStat = hp + attack + specialAttack + defense + specialDefense + speed;

        if (actualTotalStat != expectedTotalStat) {
            throw new IllegalArgumentException(pokemon.getId() + " 종족값이 일치하지 않습니다.");
        }
    }

    static void validatePokemonGeneration(List<Pokemon> pokemons) {
        Predicate<Integer> invalidGeneration = gen -> gen < MIN_GENERATION || gen > MAX_GENERATION;
        boolean validationFailed = pokemons.stream()
                .map(Pokemon::getGeneration)
                .anyMatch(invalidGeneration);

        if (validationFailed) {
            throw new IllegalArgumentException("Pokemon generation is invalid");
        }
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

        long trueCount = Arrays.stream(values)
                .filter(Boolean::booleanValue)
                .count();

        if (trueCount > 1) {
            throw new IllegalArgumentException("rarityNotConsistence");
        }
    }
}
