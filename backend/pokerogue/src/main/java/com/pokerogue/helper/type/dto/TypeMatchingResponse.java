package com.pokerogue.helper.type.dto;

import com.pokerogue.helper.type.entity.TypeMatching;

public record TypeMatchingResponse (String from, String to, int result){

    public static TypeMatchingResponse from(TypeMatching typeMatching) {
        return new TypeMatchingResponse(typeMatching.getFrom().getName(), typeMatching.getTo().getName(), typeMatching.getResult());
    }
}
