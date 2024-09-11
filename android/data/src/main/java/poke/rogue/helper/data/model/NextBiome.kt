package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.NextBiomesResponse

data class NextBiome(
    val id: String,
    val name: String,
    val image: String,
    val pokemonType: List<Type>,
    val gymLeaderType: List<Type>,
    val probability: Double,
)

fun NextBiomesResponse.toData(): NextBiome =
    NextBiome(
        id = id,
        name = name,
        image = image,
        pokemonType = pokemonTypes.toData(),
        gymLeaderType = gymLeaderTypes.toData(),
        probability = probability,
    )

fun List<NextBiomesResponse>.toData(): List<NextBiome> = map(NextBiomesResponse::toData)
