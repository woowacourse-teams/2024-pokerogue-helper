package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse2

@Serializable
data class AbilityDetailResponse2(
    @SerialName("koName")
    val name: String,
    val description: String,
    val pokemons: List<PokemonResponse2>,
)
