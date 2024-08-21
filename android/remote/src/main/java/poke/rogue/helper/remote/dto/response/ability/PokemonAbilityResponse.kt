package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityResponse(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)
