package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.BiomesResponse

data class Biome(
    val id: String,
    val name: String,
    val image: String,
    val pokemonType: List<String>,
    val gymLeaderType: List<String>,
)

fun BiomesResponse.toData(): Biome =
    Biome(
        id = id,
        name = name,
        image = image,
        pokemonType = pokemonType,
        gymLeaderType = gymLeaderType,
    )
