package poke.rogue.helper.presentation.poketmon2

import poke.rogue.helper.presentation.type.model.TypeUiModel

data class PokemonUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
) {
    companion object {
        private const val DUMMY_POKEMON_NAME = "피카츄"
        private const val DUMMY_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"

        val DUMMY =
            PokemonUiModel(
                id = -1,
                name = DUMMY_POKEMON_NAME,
                imageUrl = DUMMY_POKEMON_IMAGE_URL,
                types = TypeUiModel.entries.map { it.typeName }.take(2),
            )

        fun dummys(count: Int) =
            List(count) {
                DUMMY.copy(
                    id = it.toLong(),
                    imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/" +
                            "sprites/pokemon/other/official-artwork/${1 + it}.png",
                    types = TypeUiModel.entries.map { it.typeName }.shuffled().take(2),
                )
            }
    }
}
