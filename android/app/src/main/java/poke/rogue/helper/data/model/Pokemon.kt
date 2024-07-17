package poke.rogue.helper.data.model

data class Pokemon(
    val id: Long,
    val dexNumber: Long,
    val name: String,
    val imageUrl: String,
    val types: List<Type>,
) {
    companion object {
        private const val DUMMY_POKEMON_NAME = "이상해씨"
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        private val DUMMY_TYPES = listOf(Type.of("풀"), Type.of("독"))

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
