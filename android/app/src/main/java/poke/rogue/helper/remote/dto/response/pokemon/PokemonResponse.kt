package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse
import poke.rogue.helper.remote.dto.response.type.toData

@Serializable
data class PokemonResponse(
    val id: Long,
    val pokedexNumber: Long,
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
)

fun PokemonResponse.toData(): Pokemon =
    Pokemon(
        id = id,
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        types = types.map(PokemonTypeResponse::toData),
    )

fun List<PokemonResponse>.toData(): List<Pokemon> = map(PokemonResponse::toData)
