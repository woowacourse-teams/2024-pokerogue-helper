package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class Pokemon(
    val id: Long,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
    val generation: PokemonGeneration = PokemonGeneration.ALL,
    val baseStat: Int = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
) {
    companion object {
        private const val DUMMY_POKEMON_NAME = "이상해씨"
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        private val DUMMY_TYPES = listOf(Type.GRASS, Type.POISON)

        val DUMMY =
            Pokemon(
                id = 1,
                dexNumber = 1,
                name = DUMMY_POKEMON_NAME,
                imageUrl = DUMMY_IMAGE_URL,
                types = DUMMY_TYPES,
            )
    }
}

fun PokemonResponse.toData(): Pokemon =
    Pokemon(
        id = id,
        dexNumber = pokedexNumber,
        name = name,
        imageUrl = image,
        types = types.map(PokemonTypeResponse::toData),
    )

fun List<PokemonResponse>.toData(): List<Pokemon> = map(PokemonResponse::toData)
