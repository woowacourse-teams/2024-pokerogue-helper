package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.data.model.Ability

@Serializable
data class AbilityResponse(
    val id: Long = 0,
    val name: String,
    @SerialName("shortDescription")
    val description: String,
) {
    fun toData(): Ability =
        Ability(
            id = id,
            name = name,
            description = description,
        )
}
