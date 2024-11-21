package poke.rogue.helper.presentation.biome.detail

import poke.rogue.helper.data.model.BiomeDetail
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.biome.model.NextBiomeUiModel
import poke.rogue.helper.presentation.biome.model.toUi
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel1

class BiomeDetailUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
    val wildPokemons: List<BiomePokemonUiModel>,
    val bossPokemons: List<BiomePokemonUiModel>,
    val gymPokemons: List<BiomePokemonUiModel>,
    val nextBiomes: List<NextBiomeUiModel>,
) {
    companion object {
        private const val DUMMY_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

        fun dummyUrl(id: Long) = "$DUMMY_IMAGE_URL$id.png"

        val Default: BiomeDetailUiState =
            BiomeDetailUiState(
                id = "NO_ID",
                name = "풀숲",
                imageUrl = "",
                wildPokemons = emptyList(),
                bossPokemons = emptyList(),
                gymPokemons = emptyList(),
                nextBiomes = emptyList(),
            )
        val DUMMY: BiomeDetailUiState =
            BiomeDetailUiState(
                id = "1",
                name = "풀숲",
                imageUrl = "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                wildPokemons =
                    listOf(
                        BiomePokemonUiModel(
                            grade = "일반",
                            type = null,
                            gymLeaderUrl = null,
                            pokemons =
                                (1..9).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "일반 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.GRASS),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "희귀",
                            type = null,
                            gymLeaderUrl = null,
                            pokemons =
                                (10..21).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "희귀 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.POISON),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "전설",
                            type = null,
                            gymLeaderUrl = null,
                            pokemons =
                                (22..24).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "전설 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.GRASS, TypeUiModel1.POISON),
                                    )
                                },
                        ),
                    ),
                bossPokemons =
                    listOf(
                        BiomePokemonUiModel(
                            grade = "일반",
                            type = null,
                            gymLeaderUrl = null,
                            pokemons =
                                (990..1005).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "일반 보스 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.GRASS, TypeUiModel1.POISON),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "희귀",
                            type = null,
                            gymLeaderUrl = null,
                            pokemons =
                                (1006..1011).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "희귀 보스 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.GRASS, TypeUiModel1.POISON),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "전설",
                            gymLeaderUrl = null,
                            type = null,
                            pokemons =
                                (1012..1015).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "전설 보스 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.GRASS, TypeUiModel1.POISON),
                                    )
                                },
                        ),
                    ),
                gymPokemons =
                    listOf(
                        BiomePokemonUiModel(
                            grade = "심지박사",
                            gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:opal.png",
                            type = TypeUiModel1.FAIRY,
                            pokemons =
                                (871..874).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "심지몬 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.STEEL, TypeUiModel1.FAIRY),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "꼬상조교",
                            gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:bede.png",
                            type = TypeUiModel1.FAIRY,
                            pokemons =
                                (901..905).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "꼬상몬 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.DRAGON, TypeUiModel1.FAIRY),
                                    )
                                },
                        ),
                        BiomePokemonUiModel(
                            grade = "비토학생",
                            gymLeaderUrl = "https://wiki.pokerogue.net/_media/trainers:valerie.png",
                            type = TypeUiModel1.FAIRY,
                            pokemons =
                                (100..105).map {
                                    PokemonUiModel(
                                        dexNumber = it.toLong(),
                                        name = "비토몬 $it",
                                        imageUrl = dummyUrl(it.toLong()),
                                        types = listOf(TypeUiModel1.ICE, TypeUiModel1.POISON),
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

fun BiomeDetail.toUiState(): BiomeDetailUiState =
    BiomeDetailUiState(
        id = id,
        name = name,
        imageUrl = image,
        wildPokemons = wildPokemons.map { it.toUi() },
        bossPokemons = bossPokemons.map { it.toUi() },
        gymPokemons = gymPokemons.map { it.toUi() },
        nextBiomes = nextBiomes.map { it.toUi() },
    )
