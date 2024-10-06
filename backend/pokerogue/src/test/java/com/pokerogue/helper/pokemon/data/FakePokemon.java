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

    static Pokemon createNewPokemon() {
        try {
            Pokemon pokemon = new Pokemon();

            for (String fieldsName : fieldsNames) {
                Field declaredField = pokemon.getClass().getDeclaredField(fieldsName);
                declaredField.setAccessible(true);
                declaredField.set(pokemon, getValue(fieldsName));
            }

            return pokemon;

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValue(String fieldsName) {
        return switch (fieldsName) {
            case "id" -> "polla";
            case "imageId" -> "mia";
            case "pokedexNumber" -> 2024;
            case "name" -> "bito";
            case "koName" -> "티미룸";
            case "speciesName" -> "backend";
            case "canChangeForm" -> true;
            case "formName" -> "wooteco";
            case "baseExp" -> 50;
            case "friendship" -> 50;
            case "types" -> List.of(Type.UNKNOWN);
            case "normalAbilityIds" -> List.of("super", "nova");
            case "hiddenAbilityId" -> "nanen";
            case "passiveAbilityId" -> "dala";
            case "generation" -> 7;
            case "legendary" -> false;
            case "subLegendary" -> false;
            case "mythical" -> true;
            case "evolutions" -> List.of("developer", "dev_ops");
            case "formChanges" -> List.of("senior_dev", "tech_lead");
            case "baseTotal" -> 300;
            case "hp" -> 1;
            case "attack" -> 1;
            case "defense" -> 1;
            case "specialAttack" -> 1;
            case "specialDefense" -> 1;
            case "speed" -> 1;
            case "height" -> 1;
            case "weight" -> 1;
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
