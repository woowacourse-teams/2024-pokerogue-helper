package com.pokerogue.helper.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    POKEMON_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 포켓몬을 찾지 못했습니다."),
    POKEMON_ABILITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 특성을 찾지 못했습니다."),
    POKEMON_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 타입을 찾지 못했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
