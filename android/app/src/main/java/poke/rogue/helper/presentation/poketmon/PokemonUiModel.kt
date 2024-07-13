package poke.rogue.helper.presentation.poketmon

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val type: List<String>,
) {
    companion object {
        private const val DUMMY_POKEMON_NAME = "피카츄"
        private const val DUMMY_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"

        val DUMMY =
            PokemonUiModel(
                id = -1,
                name = DUMMY_POKEMON_NAME,
                imageUrl = DUMMY_POKEMON_IMAGE_URL,
                type = listOf("전기", "불"),
            )
    }
}
