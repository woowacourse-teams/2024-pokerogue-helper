package com.pokerogue.helper.pokemon.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormChange {

    private String from;
    private String previousForm;
    private String currentForm;
    private String item; // Todo: enum
}
