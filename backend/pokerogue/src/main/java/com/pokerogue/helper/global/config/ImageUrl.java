package com.pokerogue.helper.global.config;

public enum ImageUrl {

    BASE_URL("https://d11z3l5940xyw9.cloudfront.net/pokerogue"),
    POKEMON_FRONT("/pokemon/front/"),
    POKEMON_BACK("/pokemon/back/"),
    TYPE("/type/"),
    MOVE_CATEGORY("/move-category/"),
    BIOME("/biome/"),
    ;

    private static final String PNG = ".png";
    private final String url;

    ImageUrl(String url) {
        this.url = url;
    }

    public static String getPokemonImage(String id) {
        return BASE_URL.url + POKEMON_FRONT.url + id + PNG;
    }

    public static String getPokemonBackImage(String id) {
        return BASE_URL.url + POKEMON_BACK.url + id + PNG;
    }

    public static String getBiomeImage(String id) {
        return BASE_URL.url + BIOME.url + id + PNG;
    }

    public static String getMoveCategoryImage(String id) {
        return BASE_URL.url + MOVE_CATEGORY.url + id + PNG;
    }

    public static String getTypeImage(String id) {
        return BASE_URL.url + TYPE.url + id;
    }
}
