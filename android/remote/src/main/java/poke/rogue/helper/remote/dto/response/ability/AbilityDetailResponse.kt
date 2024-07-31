package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse

@Serializable
data class AbilityDetailResponse(
    @SerialName("koName")
    val title: String,
    val description: String,
    val pokemons: List<PokemonResponse>,
)
