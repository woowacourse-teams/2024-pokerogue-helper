package com.pokerogue.helper.external.dto;

public record Language(String name) {

    public boolean isKorean() {
        return name.equals("ko");
    }
}
