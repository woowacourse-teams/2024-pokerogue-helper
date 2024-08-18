package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.NextBiomesResponse

data class BiomeNextBiome(
    val id: String,
    val name: String,
    val image: String,
    val probability: Double,
)

fun NextBiomesResponse.toData(): BiomeNextBiome =
    BiomeNextBiome(
        id = id,
        name = name,
        image = image,
        probability = probability,
    )

fun List<NextBiomesResponse>.toData(): List<BiomeNextBiome> =
    map(NextBiomesResponse::toData)
