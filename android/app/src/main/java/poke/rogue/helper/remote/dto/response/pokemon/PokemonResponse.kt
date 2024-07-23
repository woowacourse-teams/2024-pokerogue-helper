package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonResponse(
    val id: Long,
    val image: String,
    val name: String,
    val pokedexNumber: Long,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
)
