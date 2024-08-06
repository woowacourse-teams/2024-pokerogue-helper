package com.pokerogue.external.pokemon.dto;

public record Name(Language language, String name) {

    public boolean isKorean() {
        return language.isKorean();
    }

    public boolean isEnglish() {
        return language.isEnglish();
    }
}
