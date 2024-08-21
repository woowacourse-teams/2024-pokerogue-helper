package com.pokerogue.helper.pokemon2.dto;


import java.util.Objects;

public record EvolutionResponse(
        String name,
        Integer level,
        String item,
        String condition,
        String image
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EvolutionResponse that = (EvolutionResponse) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
