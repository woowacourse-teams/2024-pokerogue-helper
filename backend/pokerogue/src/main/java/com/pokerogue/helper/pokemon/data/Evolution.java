package com.pokerogue.helper.pokemon.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class Evolution {

    private String from;
    private String level;
    private String to;
    private String item;
    private String condition;

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
