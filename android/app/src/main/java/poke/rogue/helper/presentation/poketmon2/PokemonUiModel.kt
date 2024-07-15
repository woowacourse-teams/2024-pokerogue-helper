package poke.rogue.helper.presentation.poketmon2

data class PokemonUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val types: List<String>, //TODO : TypeUiModel 으로 변경
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
                types = listOf("전기", "노말"),
            )

        fun dummys(count: Int) = List(count) {
            DUMMY.copy(
                id = it.toLong(),
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${1 + it}.png"
            )
        }
    }
}
