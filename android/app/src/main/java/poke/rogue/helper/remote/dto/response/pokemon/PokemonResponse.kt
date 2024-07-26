package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonResponse(
    val id: Long,
    val pokedexNumber: Long,
    @SerialName("koName")
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
)
