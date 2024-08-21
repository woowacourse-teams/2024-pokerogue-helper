package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Evolution

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
        const val LEVEL_DOES_NOT_MATTER = 1

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

        val DUMMY_EEVEE =
            SingleEvolutionUiModel(
                pokemonId = "eevee{Normal}",
                pokemonName = "이브이",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/133.png",
                depth = 0,
            )

        val DUMMY_SYLYEON =
            SingleEvolutionUiModel(
                pokemonId = "sylveon",
                pokemonName = "님피아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/700.png",
                condition = "친밀도 70 \n+ 페어리 타입 기술 습득",
                depth = 1,
            )

        val DUMMY_ESPEON =
            SingleEvolutionUiModel(
                pokemonId = "espeon",
                pokemonName = "에브이",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/196.png",
                condition = "친밀도 70 \n+ 낮에 레벨업",
                depth = 1,
            )

        val DUMMY_UMBREON =
            SingleEvolutionUiModel(
                pokemonId = "umbreon",
                pokemonName = "블래키",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/197.png",
                condition = "친밀도 70 \n+ 밤에 레벨업",
                depth = 1,
            )

        val DUMMY_VAPOREON =
            SingleEvolutionUiModel(
                pokemonId = "vaporeon",
                pokemonName = "샤미드",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/134.png",
                item = "물의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_JOLTEON =
            SingleEvolutionUiModel(
                pokemonId = "jolteon",
                pokemonName = "쥬피썬더",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/135.png",
                item = "천둥의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_FLAREON =
            SingleEvolutionUiModel(
                pokemonId = "flareon",
                pokemonName = "부스터",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/136.png",
                item = "화염의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_LEAFEON =
            SingleEvolutionUiModel(
                pokemonId = "leafeon",
                pokemonName = "리피아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/470.png",
                item = "리프의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_GLACEON =
            SingleEvolutionUiModel(
                pokemonId = "glaceon",
                pokemonName = "글레이시아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/471.png",
                item = "눈의 돌",
                condition = "아이템 사용",
                depth = 1,
            )
    }
}

fun Evolution.toUi(): SingleEvolutionUiModel =
    SingleEvolutionUiModel(
        pokemonId = pokemonId,
        pokemonName = pokemonName,
        imageUrl = imageUrl,
        depth = depth,
        level = level,
        item = item,
        condition = condition,
    )
