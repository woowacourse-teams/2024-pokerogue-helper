package com.pokerogue.external.pokemon.dto;

public record Language(String name) {

    public boolean isKorean() {
        return name.equals("ko");
    }

    public boolean isEnglish() {
        return name.equals("en");
    }
}
