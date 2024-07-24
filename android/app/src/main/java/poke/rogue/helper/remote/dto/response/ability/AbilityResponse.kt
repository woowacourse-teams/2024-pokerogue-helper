package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    val id: Long = 0,
    val name: String,
    val description: String,
)
