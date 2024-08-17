package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.BiomeDetailResponse

data class BiomeDetail(
    val id: String,
    val name: String,
    val image: String,
    val wildPokemons: List<WildPokemon>,
    val bossPokemons: List<BossPokemon>,
    val gymPokemons: List<GymPokemon>,
    val map: List<BiomeMap>,
)

fun BiomeDetailResponse.toData(): BiomeDetail =
    BiomeDetail(
        id = id,
        name = name,
        image = image,
        wildPokemons = wildPokemons.toData(),
        bossPokemons = bossPokemons.toData(),
        gymPokemons = gymPokemons.toData(),
        map = map.toData(),
    )
