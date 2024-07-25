package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    val id: Long = 0,
    @SerialName("koName")
    val name: String,
    val description: String,
)
