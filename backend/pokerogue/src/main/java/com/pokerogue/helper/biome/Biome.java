package com.pokerogue.helper.biome;

import java.util.List;
import java.util.Map;

public record Biome(String name, Map<String, List<String>> pokemons, List<String> mainType, List<Tranier> tranier) {
}
