package poke.rogue.helper.presentation.biome.model

import androidx.annotation.DrawableRes
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel

class BiomeDetailUiModel(
    val id: Long,
    val name: String,
    val wildPokemons: List<BiomePokemonUiModel>,
    val bossPokemons: List<BiomePokemonUiModel>,
    val trainerPokemons: List<WildPokemonUiModel>,
    val map: List<NextBiomeUiModel>,
) {
    companion object {
        val DUMMY: BiomeDetailUiModel = BiomeDetailUiModel(
            id = 1,
            name = "풀숲",
            wildPokemons = listOf(
                BiomePokemonUiModel(
                    grade = "일반",
                    typeUrl = null,
                    pokemons = // id 1번부터
                    (1..9).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "일반 $it",
                            imageUrl = "",
                            types = listOf(TypeUiModel.GRASS),
                        )
                    }
                ),
                BiomePokemonUiModel(
                    grade = "희귀",
                    typeUrl = null,
                    pokemons = // id 10번부터
                    (10..40).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "희귀 $it",
                            imageUrl = "",
                            types = listOf(TypeUiModel.POISON),
                        )
                    }
                ),
                BiomePokemonUiModel(
                    grade = "전설",
                    typeUrl = null,
                    pokemons = // id 16번부터
                    (16..20).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "전설 $it",
                            imageUrl = "",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                )
            ),
            bossPokemons = listOf(
                BiomePokemonUiModel(
                    grade = "일반",
                    typeUrl = null,
                    pokemons = // id 21번부터
                    (1..10).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "일반 보스 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                BiomePokemonUiModel(
                    grade = "희귀",
                    typeUrl = null,
                    pokemons = // id 26번부터
                    (11..14).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "희귀 보스 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                BiomePokemonUiModel(
                    grade = "전설",
                    typeUrl = null,
                    pokemons = // id 31번부터
                    (15..20).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "전설 보스 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                )
            ),
            trainerPokemons = listOf(
                WildPokemonUiModel(
                    tier = "일반",
                    pokemons = // id 36번부터
                    (36..40).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "일반 트레이너 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiModel(
                    tier = "희귀",
                    pokemons = // id 41번부터
                    (41..45).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "희귀 트레이너 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiModel(
                    tier = "전설",
                    pokemons = // id 46번부터
                    (46..50).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "전설 트레이너 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                )
            ),
            map = listOf(
                NextBiomeUiModel(
                    biome = BiomeUiModel.DUMMYS[1],
                    probability = 33.3
                ),
                NextBiomeUiModel(
                    biome = BiomeUiModel.DUMMYS[2],
                    probability = 33.3
                ),
                NextBiomeUiModel(
                    biome = BiomeUiModel.DUMMYS[3],
                    probability = 33.3
                ),
            )
        )
    }
}

data class NextBiomeUiModel(
    val biome: BiomeUiModel,
    val probability: Double
)

data class WildPokemonUiModel(
    val tier: String,
    val pokemons: List<PokemonUiModel>,
) {

}

data class BiomePokemonUiModel(
    val grade: String,
    val typeUrl: String?,
    val pokemons: List<PokemonUiModel>
) {

}
