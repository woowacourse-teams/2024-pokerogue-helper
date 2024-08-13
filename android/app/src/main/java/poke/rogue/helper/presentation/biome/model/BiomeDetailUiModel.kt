package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel

class BiomeDetailUiModel(
    val id: Long,
    val name: String,
    val wildPokemons: List<WildPokemonUiPokemon>,
    val bossPokemons: List<WildPokemonUiPokemon>,
    val trainerPokemons: List<WildPokemonUiPokemon>,
    val map: List<NextBiomeUiModel>,
) {
    companion object {
        val DUMMY: BiomeDetailUiModel = BiomeDetailUiModel(
            id = 1,
            name = "풀숲",
            wildPokemons = listOf(
                WildPokemonUiPokemon(
                    tier = "일반",
                    pokemons = // id 1번부터
                    (1..9).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "일반 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiPokemon(
                    tier = "희귀",
                    pokemons = // id 10번부터
                    (10..15).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "희귀 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiPokemon(
                    tier = "전설",
                    pokemons = // id 16번부터
                    (16..20).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "전설 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                )
            ),
            bossPokemons = listOf(
                WildPokemonUiPokemon(
                    tier = "일반",
                    pokemons = // id 21번부터
                    (21..25).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "일반 보스 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiPokemon(
                    tier = "희귀",
                    pokemons = // id 26번부터
                    (26..30).map {
                        PokemonUiModel(
                            id = it.toLong(),
                            dexNumber = it.toLong(),
                            name = "희귀 보스 포켓몬 $it",
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$it.png",
                            types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                        )
                    }
                ),
                WildPokemonUiPokemon(
                    tier = "전설",
                    pokemons = // id 31번부터
                    (31..35).map {
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
                WildPokemonUiPokemon(
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
                WildPokemonUiPokemon(
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
                WildPokemonUiPokemon(
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

class WildPokemonUiPokemon(
    val tier: String,
    val pokemons: List<PokemonUiModel>,
) {

}
