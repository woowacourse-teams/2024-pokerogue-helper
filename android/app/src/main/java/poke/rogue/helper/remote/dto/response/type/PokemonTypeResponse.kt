package poke.rogue.helper.remote.dto.response.type

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeResponse(
    val pokemonTypeName: String,
    val pokemonTypeLogo: String,
)
