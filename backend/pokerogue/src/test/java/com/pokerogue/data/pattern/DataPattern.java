package com.pokerogue.data.pattern;

import java.util.regex.Pattern;

public enum DataPattern {

    ID_PATTERN(Pattern.compile("^[a-z0-9]+(_[a-z0-9]+)*$")), // 영어 소문자와 숫자, 연속되지 않는 "_"
    MOVE_ID_PATTERN(Pattern.compile("^[a-z0-9_]+$")), // 영어 소문자와 숫자,  "_"
    NAME_PATTERN(Pattern.compile("^[a-z0-9A-Z_\\s\\W]*$")), // 영어와 특수문자 및 공백 및 숫자
    KO_NAME_PATTERN(Pattern.compile(".*[가-힣].*")), // 최소 한 자의 한글
    DESCRIPTION_PATTERN(Pattern.compile(".*[가-힣].*"));

    private final Pattern pattern;

    DataPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isNotMatch(String input) {
        if (input.isEmpty()) {
            return true;
        }
        return !pattern.matcher(input).matches();
    }
}
