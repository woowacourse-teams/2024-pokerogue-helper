package poke.rogue.helper.presentation.dex

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
                id = 1,
                name = "이상해씨",
                imageUrl = DUMMY_POKEMON_IMAGE_URL,
                types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON).map { it.typeName },
            )

        private val DUMMYS =
            listOf(
                PokemonUiModel(
                    id = 1,
                    name = "이상해씨",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 2,
                    name = "이상해풀",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 3,
                    name = "이상해꽃",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 4,
                    name = "파이리",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.FIRE).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 5,
                    name = "리자드",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.FIRE).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 6,
                    name = "리자몽",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.FIRE, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 7,
                    name = "꼬부기",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.WATER).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 8,
                    name = "어니부기",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.WATER).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 9,
                    name = "거북왕",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.WATER).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 10,
                    name = "캐터피",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 11,
                    name = "단데기",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 12,
                    name = "버터플",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 13,
                    name = "뿔충이",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 14,
                    name = "딱충이",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 15,
                    name = "독침붕",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.BUG, TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 16,
                    name = "구구",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 17,
                    name = "피죤",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 18,
                    name = "피죤투",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 19,
                    name = "꼬렛",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 20,
                    name = "레트라",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 21,
                    name = "깨비참",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 22,
                    name = "깨비드릴조",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.FLYING).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 23,
                    name = "아보",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 24,
                    name = "아보크",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 25,
                    name = "피카츄",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.ELECTRIC).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 26,
                    name = "라이츄",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.ELECTRIC).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 27,
                    name = "모래두지",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.GROUND).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 28,
                    name = "고지",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.GROUND).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 29,
                    name = "니드런",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.POISON).map { it.typeName },
                ),
                PokemonUiModel(
                    id = 30,
                    name = "니드리나",
                    imageUrl = DUMMY_POKEMON_IMAGE_URL,
                    types = listOf(TypeUiModel.POISON).map { it.typeName },
                ),
            ).map {
                it.copy(
                    imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
                            "/official-artwork/${it.id}.png",
                )
            }

        fun dummys(count: Int): List<PokemonUiModel> {
            return if (count >= 30) {
                DUMMYS
            } else {
                DUMMYS.take(count)
            }
        }
    }
}
