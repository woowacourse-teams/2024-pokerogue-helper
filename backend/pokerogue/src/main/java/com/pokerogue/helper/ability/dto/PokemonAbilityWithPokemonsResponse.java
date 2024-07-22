package com.pokerogue.helper.ability.dto;

import java.util.List;

public record PokemonAbilityWithPokemonsResponse(String name, String description, List<SameAbilityPokemonResponse> pokemons) {
}
