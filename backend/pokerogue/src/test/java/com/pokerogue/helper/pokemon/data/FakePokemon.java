package com.pokerogue.helper.pokemon.data;

import java.lang.reflect.Field;
import java.util.List;

public class FakePokemon {

    static List<String> fieldsNames = List.of(
            "id",
            "imageId",
            "pokedexNumber",
            "name",
            "koName",
            "speciesName",
            "canChangeForm",
            "formName",
            "baseExp",
            "friendship",
            "types",
            "normalAbilityIds",
            "hiddenAbilityId",
            "passiveAbilityId",
            "generation",
            "legendary",
            "subLegendary",
            "mythical",
            "evolutions",
            "formChanges",
            "baseTotal",
            "hp",
            "attack",
            "defense",
            "specialAttack",
            "specialDefense",
            "speed",
            "height",
            "weight",
            "eggMoveIds",
            "levelMoves",
            "technicalMachineMoveIds",
            "biomeIds"
    );

    public static Pokemon pokemon;

    static {
        createNewPokemon();
    }

    static Pokemon createNewPokemon() {
        try {
            Pokemon pokemon = new Pokemon();

            for (String fieldsName : fieldsNames) {
                Field declaredField = pokemon.getClass().getDeclaredField(fieldsName);
                declaredField.setAccessible(true);
                declaredField.set(pokemon, getValue(fieldsName));
            }

            Field declaredField = pokemon.getClass().getDeclaredField("id");
            declaredField.setAccessible(true);
            declaredField.set(pokemon, "pikachu");
            return pokemon;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValue(String fieldsName) {
        return switch (fieldsName) {
            case "id" -> "polla";
            case "imageId" -> "mia";
            case "pokedexNumber" -> 1;
            case "name" -> "bito";
            case "koName" -> "티미룸";
            case "speciesName" -> "backend";
            case "canChangeForm" -> true;
            case "formName" -> "g_max";
            case "baseExp" -> 50;
            case "friendship" -> 50;
            case "types" -> List.of(Type.BUG);
            case "normalAbilityIds" -> List.of("a", "b");
            case "hiddenAbilityId" -> "a";
            case "passiveAbilityId" -> "a";
            case "generation" -> 1;
            case "legendary" -> false;
            case "subLegendary" -> false;
            case "mythical" -> false;
            case "evolutions" -> List.of();
            case "formChanges" -> List.of();
            case "baseTotal" -> 300;
            case "hp" -> 10;
            case "attack" -> 10;
            case "defense" -> 10;
            case "specialAttack" -> 10;
            case "specialDefense" -> 10;
            case "speed" -> 10;
            case "height" -> 10;
            case "weight" -> 10;
            case "eggMoveIds" -> List.of();
            case "levelMoves" -> List.of();
            case "technicalMachineMoveIds" -> List.of();
            case "biomeIds" -> List.of();
            default -> throw new IllegalStateException("Unexpected value: " + fieldsName);
        };

    }

    public static void setField(Pokemon pokemon, String fieldName, Object value) {
        try {
            Field declaredField = pokemon.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(pokemon, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
