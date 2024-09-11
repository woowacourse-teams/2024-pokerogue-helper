package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionResponse(
    @SerialName("id")
    val pokemonId: String = "",
    @SerialName("name")
    val pokemonName: String,
    @SerialName("image")
    val imageUrl: String,
    val depth: Int,
    val level: Int,
    val item: String? = null,
    val condition: String? = null,
)

@Serializable
data class EvolutionsResponse(
    val currentDepth: Int,
    @SerialName("stages")
    val evolutions: List<EvolutionResponse>,
)
