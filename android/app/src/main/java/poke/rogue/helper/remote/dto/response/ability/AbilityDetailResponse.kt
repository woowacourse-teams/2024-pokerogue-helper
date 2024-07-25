package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse

@Serializable
data class AbilityDetailResponse(
    val id: Long = 0,
    @SerialName("koName")
    val name: String,
    val description: String,
    val pokemons: List<PokemonResponse>,
)
