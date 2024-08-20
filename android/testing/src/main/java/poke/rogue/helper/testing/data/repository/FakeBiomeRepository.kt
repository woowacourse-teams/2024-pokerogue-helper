package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail
import poke.rogue.helper.data.model.BiomeNextBiome
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.biome.BiomePokemon
import poke.rogue.helper.data.model.biome.BossPokemon
import poke.rogue.helper.data.model.biome.GymPokemon
import poke.rogue.helper.data.model.biome.WildPokemon
import poke.rogue.helper.data.repository.BiomeRepository

class FakeBiomeRepository : BiomeRepository {
    override suspend fun biomes(): List<Biome> = BIOMES

    override suspend fun biomeDetail(id: String): BiomeDetail = BIOME_DETAIL[id] ?: throw IllegalArgumentException("Invalid biome ID")

    companion object {
        val BIOMES: List<Biome> =
            listOf(
                Biome(
                    id = "grass",
                    name = "풀숲",
                    image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                    pokemonType = listOf("Grass", "Bug"),
                    gymLeaderType = listOf("Grass"),
                ),
                Biome(
                    id = "tall_grass",
                    name = "높은 풀숲",
                    image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_tall_grass_bg.png?w=200&tok=b3497c",
                    pokemonType = listOf("Bug"),
                    gymLeaderType = listOf("Bug"),
                ),
                Biome(
                    id = "cave",
                    name = "동굴",
                    image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_cave_bg.png?w=200&tok=905d8b",
                    pokemonType = listOf("Grass"),
                    gymLeaderType = listOf("Grass"),
                ),
                Biome(
                    id = "badlands",
                    name = "악지",
                    image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_badlands_bg.png?w=200&tok=37d070",
                    pokemonType = listOf("Dark", "Fighting"),
                    gymLeaderType = listOf("Dark", "Fighting"),
                ),
            )

        val BIOME_DETAIL: Map<String, BiomeDetail> =
            mapOf(
                "grass" to
                    BiomeDetail(
                        id = "grass",
                        name = "풀숲",
                        image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                        wildPokemons =
                            listOf(
                                WildPokemon(
                                    "레어",
                                    listOf(
                                        BiomePokemon(
                                            id = "이상해씨",
                                            name = "이상해씨",
                                            image = "",
                                            types = listOf(Type.BUG, Type.GRASS),
                                        ),
                                    ),
                                ),
                            ),
                        bossPokemons =
                            listOf(
                                BossPokemon(
                                    "레어",
                                    listOf(
                                        BiomePokemon(
                                            id = "이상해풀",
                                            name = "이상해풀",
                                            image = "",
                                            types = listOf(Type.GRASS, Type.POISON),
                                        ),
                                    ),
                                ),
                            ),
                        gymPokemons =
                            listOf(
                                GymPokemon(
                                    gymLeaderName = "오박사",
                                    gymLeaderImage = "",
                                    gymLeaderLogos = emptyList(),
                                    pokemons =
                                        listOf(
                                            BiomePokemon(
                                                id = "이상해꽃",
                                                name = "이상해꽃",
                                                image = "",
                                                types = listOf(Type.GRASS, Type.POISON),
                                            ),
                                        ),
                                ),
                            ),
                        nextBiomes =
                            listOf(
                                BiomeNextBiome(
                                    id = "tall_grass",
                                    name = "높은 풀숲",
                                    image = "https://wiki.pokerogue.net/_media/ko:biomes:ko_tall_grass_bg.png?w=200&tok=b3497c",
                                    probability = 0.5,
                                ),
                            ),
                    ),
            )
    }
}
