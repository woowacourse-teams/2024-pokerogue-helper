package com.pokerogue.helper.pokemon.dto;

import java.util.List;

public record EvolutionResponses(int currentDepth, List<EvolutionResponse> stages) {
}
