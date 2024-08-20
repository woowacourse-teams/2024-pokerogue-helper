package com.pokerogue.helper.pokemon2.dto;

import jakarta.annotation.Nullable;

@Nullable
record MoveResponse(
        String name,
        Integer level,
        Integer power,
        Integer accuracy,
        String type,
        String category) {

}
