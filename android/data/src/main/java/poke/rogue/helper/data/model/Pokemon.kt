package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class Pokemon(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
){
    companion object {
        private const val DUMMY_POKEMON_NAME = "이상해씨"
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        private val DUMMY_TYPES = listOf(Type.GRASS, Type.POISON)

        val DUMMY =
            Pokemon(
                id = "1",
                dexNumber = 1,
                name = DUMMY_POKEMON_NAME,
                imageUrl = DUMMY_IMAGE_URL,
                types = DUMMY_TYPES,
            )
    }
}

fun PokemonResponse.toData(): Pokemon =
    Pokemon(
        id = id.toString(),
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        types = types.map(PokemonTypeResponse::toData),
    )

fun List<PokemonResponse>.toData(): List<Pokemon> = map(PokemonResponse::toData)
