package poke.rogue.helper.presentation.dex.model

data class SingleEvolutionUiModel(
    val pokemonId: String,
    val pokemonName: String,
    val depth: Int,
    val level: Int,
    val item: String?,
    val condition: String?,
) {
    companion object {
        const val LEVEL_DOES_NOT_MATTER = 1

        val DUMMY_PICHU =
            SingleEvolutionUiModel(
                pokemonId = "pichu",
                pokemonName = "피츄",
                depth = 0,
                level = LEVEL_DOES_NOT_MATTER,
                item = null,
                condition = null,
            )

        val DUMMY_PIKACHU =
            SingleEvolutionUiModel(
                pokemonId = "pikachu{Normal}",
                pokemonName = "피카츄",
                depth = 1,
                level = LEVEL_DOES_NOT_MATTER,
                item = null,
                condition = "친밀도 90",
            )

        private val DUMMY_RAICHU =
            SingleEvolutionUiModel(
                pokemonId = "raichu{Normal}",
                pokemonName = "라이츄",
                depth = 0,
                level = LEVEL_DOES_NOT_MATTER,
                item = "천둥의 돌",
                condition = null,
            )

        private val DUMMY_ALOLA_RAICHU =
            SingleEvolutionUiModel(
                pokemonId = "raichu{Alola}",
                pokemonName = "라이츄{알로라}",
                depth = 2,
                level = LEVEL_DOES_NOT_MATTER,
                item = "천둥의 돌",
                condition = "섬, 해변에서 아이템 사용",
            )

        private val DUMMY_GIGA_PIKACHU =
            SingleEvolutionUiModel(
                pokemonId = "pikachu{G-Max} ",
                pokemonName = "피카츄{G-Max}",
                depth = 2,
                level = LEVEL_DOES_NOT_MATTER,
                item = "다이 맥스 버섯",
                condition = null,
            )

        val DUMMY_PICHU_EVOLUTION =
            listOf(
                DUMMY_PICHU,
                DUMMY_PIKACHU,
                DUMMY_RAICHU,
                DUMMY_ALOLA_RAICHU,
                DUMMY_GIGA_PIKACHU,
            )

        val DUMMY_PICAKCHU_EVOLUTION =
            listOf(
                DUMMY_RAICHU,
                DUMMY_ALOLA_RAICHU,
                DUMMY_GIGA_PIKACHU,
            )
    }
}
