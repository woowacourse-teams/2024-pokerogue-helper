package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse2(
    val id: String,
    @SerialName("koName")
    val name: String,
    val description: String,
)
