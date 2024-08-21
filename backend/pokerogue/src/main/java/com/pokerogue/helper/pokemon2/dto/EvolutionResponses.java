package com.pokerogue.helper.pokemon2.dto;

import java.util.List;

public record EvolutionResponses(int currentStage, List<List<EvolutionResponse>> stages) {

}
