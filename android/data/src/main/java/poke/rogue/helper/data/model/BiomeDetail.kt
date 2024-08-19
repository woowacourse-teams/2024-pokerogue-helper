package poke.rogue.helper.data.model

import poke.rogue.helper.data.model.biome.BossPokemon
import poke.rogue.helper.data.model.biome.GymPokemon
import poke.rogue.helper.data.model.biome.WildPokemon
import poke.rogue.helper.data.model.biome.toData
import poke.rogue.helper.remote.dto.response.biomes.BiomeDetailResponse

data class BiomeDetail(
    val id: String,
    val name: String,
    val image: String,
    val wildPokemons: List<WildPokemon>,
    val bossPokemons: List<BossPokemon>,
    val gymPokemons: List<GymPokemon>,
    val nextBiomes: List<BiomeNextBiome>,
)

fun BiomeDetailResponse.toData(): BiomeDetail =
    BiomeDetail(
        id = id,
        name = name,
        image = image,
        wildPokemons = wildPokemons.toData(),
        bossPokemons = bossPokemons.toData(),
        gymPokemons = gymPokemons.toData(),
        nextBiomes = nextBiomes.toData(),
    )
