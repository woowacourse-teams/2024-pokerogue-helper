package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel

class BiomeDetailUiModel(
    val id: Long,
    val name: String,
    val wildPokemons: List<BiomePokemonUiModel>,
    val bossPokemons: List<BiomePokemonUiModel>,
    val gymPokemons: List<BiomePokemonUiModel>,
    val nextBiomes: List<NextBiomeUiModel>,
) {
    companion object {
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

        fun dummyUrl(id: Long) = "$DUMMY_IMAGE_URL$id.png"

        val DUMMY: BiomeDetailUiModel =
            BiomeDetailUiModel(
                id = 1,
                name = "풀숲",
                wildPokemons =
                listOf(
                    BiomePokemonUiModel(
                        grade = "일반",
                        typeUrl = null,
                        gymLeaderUrl = null,
                        pokemons =
                        (1..9).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "일반 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.GRASS),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "희귀",
                        typeUrl = null,
                        gymLeaderUrl = null,
                        pokemons =
                        (10..21).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "희귀 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.POISON),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "전설",
                        typeUrl = null,
                        gymLeaderUrl = null,
                        pokemons =
                        (22..24).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "전설 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                            )
                        },
                    ),
                ),
                bossPokemons =
                listOf(
                    BiomePokemonUiModel(
                        grade = "일반",
                        typeUrl = null,
                        gymLeaderUrl = null,
                        pokemons =
                        (990..1005).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "일반 보스 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "희귀",
                        typeUrl = null,
                        gymLeaderUrl = null,
                        pokemons =
                        (1006..1011).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "희귀 보스 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "전설",
                        gymLeaderUrl = null,
                        typeUrl = null,
                        pokemons =
                        (1012..1015).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "전설 보스 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                            )
                        },
                    ),
                ),
                gymPokemons =
                listOf(
                    BiomePokemonUiModel(
                        grade = "심지박사",
                        gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:opal.png",
                        typeUrl = "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/type/fairy.svg",
                        pokemons =
                        (871..874).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "심지몬 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.STEEL, TypeUiModel.FAIRY),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "꼬상조교",
                        gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:bede.png",
                        typeUrl = "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/type/dragon.svg",
                        pokemons =
                        (901..905).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "꼬상몬 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.DRAGON, TypeUiModel.FAIRY),
                            )
                        },
                    ),
                    BiomePokemonUiModel(
                        grade = "비토학생",
                        gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:valerie.png",
                        typeUrl = "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/type/poison.svg",
                        pokemons =
                        (100..105).map {
                            PokemonUiModel(
                                id = it.toLong(),
                                dexNumber = it.toLong(),
                                name = "비토몬 $it",
                                imageUrl = dummyUrl(it.toLong()),
                                types = listOf(TypeUiModel.ICE, TypeUiModel.POISON),
                            )
                        },
                    ),
                ),
                nextBiomes =
                listOf(
                    NextBiomeUiModel(
                        biome = BiomeUiModel.DUMMYS[1],
                        probability = 33.3,
                    ),
                    NextBiomeUiModel(
                        biome = BiomeUiModel.DUMMYS[2],
                        probability = 33.3,
                    ),
                    NextBiomeUiModel(
                        biome = BiomeUiModel.DUMMYS[3],
                        probability = 33.3,
                    ),
                ),
            )
    }
}

data class NextBiomeUiModel(
    val biome: BiomeUiModel,
    val probability: Double,
)

data class BiomePokemonUiModel(
    val grade: String,
    val gymLeaderUrl: String?,
    val typeUrl: String?,
    val pokemons: List<PokemonUiModel>,
)
