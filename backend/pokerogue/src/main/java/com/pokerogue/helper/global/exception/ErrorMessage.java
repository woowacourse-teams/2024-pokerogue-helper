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
    TIER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 티어를 찾지 못했습니다."),
    TRAINER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 트레이너를 찾지 못했습니다."),
    BIOME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 바이옴을 찾지 못했습니다."),
    MOVE_BY_MACHINE_NOT_FOUND(HttpStatus.NOT_FOUND, "도감 번호에 해당하는 머신으로 배울 수 있는 기술을 찾지 못했습니다."),
    MOVE_BY_EGG_NOT_FOUND(HttpStatus.NOT_FOUND, "도감 번호에 해당하는 알 기술을 찾지 못했습니다."),
    MOVE_BY_SELF_NOT_FOUND(HttpStatus.NOT_FOUND, "도감 번호에 해당하는 자력 기술을 찾지 못했습니다."),
    MOVE_NOT_FOUND(HttpStatus.NOT_FOUND, "id에 해당하는 기술을 찾지 못했습니다."),
    MOVE_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "기술 카테고리를 찾지 못했습니다."),
    WEATHER_NOT_FOUND(HttpStatus.NOT_FOUND, "id에 해당하는 날씨를 찾지 못했습니다."),

    PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파싱에 실패했습니다."),
    TYPE_MATCHING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "타입 상성 찾기에 실패했습니다."),

    FILE_ACCESS_FAILED(HttpStatus.BAD_REQUEST, "파일 정보 접근에 실패했습니다."),
    FILE_EXTENSION_NOT_APPLY(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
