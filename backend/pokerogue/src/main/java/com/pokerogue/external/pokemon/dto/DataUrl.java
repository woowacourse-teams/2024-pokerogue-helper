package com.pokerogue.external.pokemon.dto;

public record DataUrl(String name, String url) {

    public String getUrlId() {
        String[] tokens = url().split("/");
        return tokens[tokens.length - 1];
    }
}
