package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.EvolutionResponse
import poke.rogue.helper.remote.dto.response.pokemon.EvolutionsResponse

data class Evolution(
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
            Evolution(
                pokemonId = "pichu",
                pokemonName = "피츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/172.png",
                depth = 0,
            )

        val DUMMY_PIKACHU =
            Evolution(
                pokemonId = "pikachu{Normal}",
                pokemonName = "피카츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                depth = 1,
                condition = "친밀도 90",
            )

        val DUMMY_RAICHU =
            Evolution(
                pokemonId = "raichu{Normal}",
                pokemonName = "라이츄",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/26.png",
                depth = 2,
                item = "천둥의 돌",
                condition = "아이템 사용",
            )

        val DUMMY_ALOLA_RAICHU =
            Evolution(
                pokemonId = "raichu{Alola}",
                pokemonName = "라이츄{알로라}",
                imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/full/002602.png",
                depth = 2,
                item = "천둥의 돌",
                condition = "섬, 해변에서 아이템 사용",
            )

        val DUMMY_GIGA_PIKACHU =
            Evolution(
                pokemonId = "pikachu{G-Max} ",
                pokemonName = "피카츄{G-Max}",
                imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/full/002502.png",
                depth = 2,
                item = "다이 맥스 버섯",
                condition = "아이템 사용",
            )

        val DUMMY_PSYDUCK =
            Evolution(
                pokemonId = "psyduck",
                pokemonName = "고라파덕",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/54.png",
                depth = 0,
            )

        val DUMMY_GOLDUCK =
            Evolution(
                pokemonId = "golduck",
                pokemonName = "골덕",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/55.png",
                level = 33,
                depth = 1,
            )

        val DUMMY_EEVEE =
            Evolution(
                pokemonId = "eevee{Normal}",
                pokemonName = "이브이",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/133.png",
                depth = 0,
            )

        val DUMMY_SYLYEON =
            Evolution(
                pokemonId = "sylveon",
                pokemonName = "님피아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/700.png",
                condition = "친밀도 70 \n+ 페어리 타입 기술 습득",
                depth = 1,
            )

        val DUMMY_ESPEON =
            Evolution(
                pokemonId = "espeon",
                pokemonName = "에브이",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/196.png",
                condition = "친밀도 70 \n+ 낮에 레벨업",
                depth = 1,
            )

        val DUMMY_UMBREON =
            Evolution(
                pokemonId = "umbreon",
                pokemonName = "블래키",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/197.png",
                condition = "친밀도 70 \n+ 밤에 레벨업",
                depth = 1,
            )

        val DUMMY_VAPOREON =
            Evolution(
                pokemonId = "vaporeon",
                pokemonName = "샤미드",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/134.png",
                item = "물의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_JOLTEON =
            Evolution(
                pokemonId = "jolteon",
                pokemonName = "쥬피썬더",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/135.png",
                item = "천둥의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_FLAREON =
            Evolution(
                pokemonId = "flareon",
                pokemonName = "부스터",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/136.png",
                item = "화염의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_LEAFEON =
            Evolution(
                pokemonId = "leafeon",
                pokemonName = "리피아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/470.png",
                item = "리프의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_GLACEON =
            Evolution(
                pokemonId = "glaceon",
                pokemonName = "글레이시아",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/471.png",
                item = "눈의 돌",
                condition = "아이템 사용",
                depth = 1,
            )

        val DUMMY_PICAKCHU_EVOLUTION =
            listOf(
                DUMMY_PICHU,
                DUMMY_PIKACHU,
                DUMMY_RAICHU,
                DUMMY_ALOLA_RAICHU,
                DUMMY_GIGA_PIKACHU,
            )

        val DUMMY_PSYDUCK_EVOLUTION =
            listOf(
                DUMMY_PSYDUCK,
                DUMMY_GOLDUCK,
            )

        val DUMMY_EVE_EVOLUTION =
            listOf(
                DUMMY_EEVEE,
                DUMMY_SYLYEON,
                DUMMY_ESPEON,
                DUMMY_UMBREON,
                DUMMY_VAPOREON,
                DUMMY_JOLTEON,
                DUMMY_FLAREON,
                DUMMY_LEAFEON,
                DUMMY_GLACEON,
            )
    }
}

fun EvolutionResponse.toData(): Evolution =
    Evolution(
        pokemonId = pokemonId,
        pokemonName = pokemonName,
        imageUrl = imageUrl,
        depth = depth,
        level = level,
        item = item,
        condition = condition,
    )

fun List<EvolutionResponse>.toData(): List<Evolution> = map(EvolutionResponse::toData)

fun EvolutionsResponse.toData(): List<Evolution> = evolutions.flatten().toData()
