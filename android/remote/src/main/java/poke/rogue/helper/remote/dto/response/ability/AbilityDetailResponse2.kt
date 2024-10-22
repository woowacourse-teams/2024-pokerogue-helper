package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityDetailResponse2(
    @SerialName("koName")
    val name: String,
    val description: String,
    val pokemons: List<AbilityPokemonResponse>,
)
