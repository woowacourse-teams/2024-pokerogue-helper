package com.pokerogue.helper.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    POKEMON_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 포켓몬을 찾지 못했습니다."),
    POKEMON_ABILITY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 특성을 찾지 못했습니다."),
    POKEMON_TYPE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 타입을 찾지 못했습니다."),
    ARTICLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 꿀팁을 찾지 못했습니다."),
    MOVE_TARGET_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "기술 타켓을 찾지 못했습니다."),
    MOVE_FLAG_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "기술 플래그를 찾지 못했습니다."),
    WEATHER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "id에 해당하는 날씨를 찾지 못했습니다."),
    EVOLUTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 진화 정보를 찾지 못했습니다."),
    ITEM_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 아이템을 찾지 못했습니다."),

    TIER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 티어를 찾지 못했습니다."),
    BIOME_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 바이옴을 찾지 못했습니다."),
    MOVE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "id에 해당하는 기술을 찾지 못했습니다."),
    MOVE_CATEGORY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "기술 카테고리를 찾지 못했습니다."),
    PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파싱에 실패했습니다."),
    TYPE_MATCHING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "타입 상성 찾기에 실패했습니다."),
    POKEMON_SIZE_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "예상 포켓몬 수와 실제 데이터가 일치하지 않습니다."),
    POKEMON_ID_UNEXPECTED_LETTER(HttpStatus.INTERNAL_SERVER_ERROR, "아이디에 허용되지 않은 문자가 포함되어 있습니다."),
    POKEMON_ID_DELIMITER_PLACED_IN_EDGE(HttpStatus.INTERNAL_SERVER_ERROR, "아이디에 구분자가 처음이나 끝에 올 수 없습니다."),
    POKEMON_ID_DELIMITER_IS_SEQUENTIAL(HttpStatus.INTERNAL_SERVER_ERROR, "아이디에 구분자가 연속으로 배치되어 있습니다."),
    POKEMON_GENERATION_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR,"적절하지 않은 포켓몬 세대입니다."),
    POKEMON_BASE_TOTAL_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR,"종족값이 기본스탯의 합과 다릅니다."),
    POKEMON_FORM_CHANGE_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR,"폼변환이 가능하지만 변환 가능한 포켓몬이 없습니다."),
    POKEMON_RARITY_COUNT_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR,"전설, 준전설, 미신은 셋 중 하나 이하만 선택될 수 있습니다."),
    POKEMON_NORMAL_ABILITY_COUNT(HttpStatus.INTERNAL_SERVER_ERROR,"기본 특성은 하나 혹은 두개여야 합니다."),
    POKEMON_ABILITY_DUPLICATION(HttpStatus.INTERNAL_SERVER_ERROR,"중복되는 특성이 존재합니다."),


    FILE_ACCESS_FAILED(HttpStatus.BAD_REQUEST, "파일 정보 접근에 실패했습니다."),
    FILE_EXTENSION_NOT_APPLY(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
