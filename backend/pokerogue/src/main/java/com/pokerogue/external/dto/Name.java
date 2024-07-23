package com.pokerogue.external.dto;

public record Name(Language language, String name) {

    public boolean isKorean() {
        return language.isKorean();
    }
}
