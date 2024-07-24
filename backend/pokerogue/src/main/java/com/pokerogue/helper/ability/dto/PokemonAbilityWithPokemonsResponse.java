package com.pokerogue.helper.ability.dto;

import java.util.List;

public record PokemonAbilityWithPokemonsResponse(String koName, String description, List<SameAbilityPokemonResponse> pokemons) {
}
