package com.pokerogue.helper.pokemon.config;

public enum ImageUrl {
    BASE_URL("https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue"),
    POKEMON_FRONT("/pokemon/front/"),
    POKEMON_BACK("/pokemon/back/"),
    TYPE("/type/"),
    MOVE_CATEGORY("/move-category/"),
    BIOME("/biome/"),
    ;

    private final String url;

    ImageUrl(String url) {
        this.url = url;
    }

    public static String getPokemonImage(String id) {
        return BASE_URL.url + POKEMON_FRONT.url + id + ".png";
    }

    public static String getPokemonBackImage(String id) {
        return BASE_URL.url + POKEMON_BACK.url + id + ".png";
    }

    public static String getBiomeImage(String id) {
        return BASE_URL.url + BIOME.url + id + ".png";
    }
}
