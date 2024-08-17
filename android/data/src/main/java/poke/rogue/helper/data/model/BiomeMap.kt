package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.MapResponse

data class BiomeMap(
    val id: String,
    val name: String,
    val image: String,
    val probability: Double,
)

fun MapResponse.toData(): BiomeMap =
    BiomeMap(
        id = id,
        name = name,
        image = image,
        probability = probability,
    )

fun List<MapResponse>.toData(): List<BiomeMap> =
    map(MapResponse::toData)
