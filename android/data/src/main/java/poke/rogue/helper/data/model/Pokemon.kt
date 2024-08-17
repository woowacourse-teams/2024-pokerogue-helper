package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class Pokemon(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
)

fun PokemonResponse.toData(): Pokemon =
    Pokemon(
        id = id.toString(),
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        types = types.map(PokemonTypeResponse::toData),
    )

fun List<PokemonResponse>.toData(): List<Pokemon> = map(PokemonResponse::toData)
