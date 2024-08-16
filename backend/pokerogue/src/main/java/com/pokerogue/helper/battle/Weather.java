package com.pokerogue.helper.battle;

import java.util.List;

public record Weather(
        String id,
        String name,
        String description,
        List<String> effects
) {
}
