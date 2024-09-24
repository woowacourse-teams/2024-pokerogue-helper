package com.pokerogue.helper.pokemon.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Evolution {

    private final String from;
    private final String level;
    private final String to;
    private final String item;
    private final String condition;

    public String from() { // Todo: 이 메소드들 지우기
        return from;
    }

    public String level() {
        return level;
    }

    public String to() {
        return to;
    }

    public String item() {
        return item;
    }

    public String condition() {
        return condition;
    }
}