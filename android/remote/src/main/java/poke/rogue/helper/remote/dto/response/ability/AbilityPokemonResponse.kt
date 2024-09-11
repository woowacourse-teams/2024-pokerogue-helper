package poke.rogue.helper.remote.dto.response.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class AbilityPokemonResponse(
    val id: String,
    val pokedexNumber: Long,
    @SerialName("koName")
    val name: String,
    val image: String,
    val pokemonTypeResponses: List<PokemonTypeResponse>,
)
