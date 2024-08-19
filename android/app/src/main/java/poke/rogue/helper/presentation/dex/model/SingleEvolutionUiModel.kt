package poke.rogue.helper.presentation.dex.model

data class SingleEvolutionUiModel(
    val pokemonId: String,
    val pokemonName: String,
    val imageUrl: String,
    val depth: Int,
    val level: Int = LEVEL_DOES_NOT_MATTER,
    val item: String? = null,
    val condition: String? = null,
) {
    companion object {
        private const val LEVEL_DOES_NOT_MATTER = 1

        val DUMMY_PICHU =
            SingleEvolutionUiModel(
                pokemonId = "pichu",
                pokemonName = "피츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/172.png",
                depth = 0,
            )

        val DUMMY_PIKACHU =
            SingleEvolutionUiModel(
                pokemonId = "pikachu{Normal}",
                pokemonName = "피카츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                depth = 1,
                condition = "친밀도 90",
            )

        val DUMMY_RAICHU =
            SingleEvolutionUiModel(
                pokemonId = "raichu{Normal}",
                pokemonName = "라이츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/26.png",
                depth = 2,
                item = "천둥의 돌",
                condition = "아이템 사용",
            )

        val DUMMY_ALOLA_RAICHU =
            SingleEvolutionUiModel(
                pokemonId = "raichu{Alola}",
                pokemonName = "라이츄{알로라}",
                imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/full/002602.png",
                depth = 2,
                item = "천둥의 돌",
                condition = "섬, 해변에서 아이템 사용",
            )

        val DUMMY_GIGA_PIKACHU =
            SingleEvolutionUiModel(
                pokemonId = "pikachu{G-Max} ",
                pokemonName = "피카츄{G-Max}",
                imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/full/002502.png",
                depth = 2,
                item = "다이 맥스 버섯",
                condition = "아이템 사용",
            )

        val DUMMY_PSYDUCK =
            SingleEvolutionUiModel(
                pokemonId = "psyduck",
                pokemonName = "고라파덕",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/54.png",
                depth = 0,
            )

        val DUMMY_GOLDUCK =
            SingleEvolutionUiModel(
                pokemonId = "golduck",
                pokemonName = "골덕",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/55.png",
                level = 33,
                depth = 1,
            )
    }
}
