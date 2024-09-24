package com.pokerogue.helper.pokemon.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FormChange {

    private final String from;
    private final String previousForm;
    private final String currentForm;
    private final String item; // Todo: enum
}
