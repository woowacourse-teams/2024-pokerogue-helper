package poke.rogue.helper.remote.dto.response.biomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BiomesResponse(
    val id: String,
    val name: String,
    val image: String,
    @SerialName("pokemonTypeLogos")
    val pokemonType: List<String>,
    @SerialName("trainerTypeLogos")
    val gymLeaderType: List<String>,
)

