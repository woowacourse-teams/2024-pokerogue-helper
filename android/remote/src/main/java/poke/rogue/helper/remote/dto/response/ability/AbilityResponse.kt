package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    val id: Long = 0,
    @SerialName("koName")
    val title: String,
    val description: String,
)

@Serializable
data class AbilityResponse2(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)
