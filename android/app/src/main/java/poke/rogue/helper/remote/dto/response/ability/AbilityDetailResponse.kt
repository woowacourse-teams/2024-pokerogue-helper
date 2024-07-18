package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.poke.PokemonResponse

@Serializable
data class AbilityDetailResponse(
    val id: Long = 0,
    val name: String,
    val description: String,
    val detailDescription: String,
    val pokemons: List<PokemonResponse>
)
