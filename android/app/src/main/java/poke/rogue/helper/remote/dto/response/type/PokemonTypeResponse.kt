package poke.rogue.helper.remote.dto.response.type

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeResponse(
    val pokemonTypeLogo: String,
    val pokemonTypeName: String
)