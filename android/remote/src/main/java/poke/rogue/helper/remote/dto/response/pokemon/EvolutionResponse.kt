package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionResponse(
    val pokemonId: String,
    val pokemonName: String,
    val imageUrl: String,
    val depth: Int,
    val level: Int,
    val item: String? = null,
    val condition: String? = null,
)
