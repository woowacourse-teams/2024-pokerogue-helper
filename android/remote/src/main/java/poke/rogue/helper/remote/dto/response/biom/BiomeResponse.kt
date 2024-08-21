package poke.rogue.helper.remote.dto.response.biom

import kotlinx.serialization.Serializable

@Serializable
data class BiomeResponse(
    val id: String,
    val name: String,
)
