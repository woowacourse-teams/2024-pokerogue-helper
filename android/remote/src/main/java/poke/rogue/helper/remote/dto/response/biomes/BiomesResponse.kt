package poke.rogue.helper.remote.dto.response.biomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class BiomesResponse(
    val id: String,
    val name: String,
    val image: String,
    @SerialName("pokemonTypeLogos")
    val pokemonType: List<PokemonTypeResponse>,
    @SerialName("trainerTypeLogos")
    val gymLeaderType: List<PokemonTypeResponse>,
)
