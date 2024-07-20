package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.NameAndUrl;
import java.util.List;

public record PokemonDetails(int weight, int height, NameAndUrl species, int hp, int attack, int defense, int speed, int specialAttack, int specialDefense, int totalStats, List<String> abilityNameList, List<String> typeNameList) {
}
