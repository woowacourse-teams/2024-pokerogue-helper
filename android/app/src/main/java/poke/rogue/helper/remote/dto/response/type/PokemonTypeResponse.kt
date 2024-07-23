package poke.rogue.helper.remote.dto.response.type

import kotlinx.serialization.Serializable
import poke.rogue.helper.data.model.Type
import java.util.Locale

@Serializable
data class PokemonTypeResponse(
    val pokemonTypeName: String,
    val pokemonTypeLogo: String,
)

fun PokemonTypeResponse.toData(): Type = Type.valueOf(pokemonTypeName.uppercase(Locale.ROOT))

fun List<PokemonTypeResponse>.toData(): List<Type> = map(PokemonTypeResponse::toData)
