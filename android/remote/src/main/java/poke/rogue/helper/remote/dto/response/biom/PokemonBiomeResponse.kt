package poke.rogue.helper.remote.dto.response.biom

import kotlinx.serialization.Serializable

@Serializable
data class PokemonBiomeResponse(
    val id: String,
    val name: String,
    val image: String,
)
