package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.data.model.NextBiome
import poke.rogue.helper.presentation.type.model.toUi

data class NextBiomeUiModel(
    val biome: BiomeUiModel,
    val probability: Double,
)

fun NextBiome.toUi(): NextBiomeUiModel =
    NextBiomeUiModel(
        biome =
            BiomeUiModel(
                id = id,
                name = name,
                imageUrl = image,
                types = (gymLeaderType.toUi() + pokemonType.toUi()).distinct().take(4),
            ),
        probability = probability,
    )
