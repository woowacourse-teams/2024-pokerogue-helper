package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class NewPokemon(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
)

fun PokemonResponse.toData(): NewPokemon =
    NewPokemon(
        id = id.toString(),
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        types = types.map(PokemonTypeResponse::toData),
    )

fun List<PokemonResponse>.toData(): List<NewPokemon> = map(PokemonResponse::toData)
