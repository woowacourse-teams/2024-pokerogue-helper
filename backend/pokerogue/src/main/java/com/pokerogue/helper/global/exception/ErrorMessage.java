package com.pokerogue.helper.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  
    POKEMON_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 포켓몬을 찾지 못했습니다."),
    POKEMON_ABILITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 특성을 찾지 못했습니다."),
    POKEMON_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 타입을 찾지 못했습니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 꿀팁을 찾지 못했습니다."),
    MACHINE_MOVE_NOT_FOUND(HttpStatus.NOT_FOUND, "포켓몬 도감 번호로 머신 기술을 찾지 못했습니다."),
    MOVE_NOT_FOUND(HttpStatus.NOT_FOUND, "id에 해당하는 기술을 찾지 못했습니다."),
  
    FILE_EXTENSION_NOT_APPLY(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    FILE_ACCESS_FAILED(HttpStatus.BAD_REQUEST, "파일 정보 접근에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
