package poke.rogue.helper.data.model.biome

import poke.rogue.helper.remote.dto.response.biomes.BossPokemonResponse

data class BossPokemon(
    val tier: String,
    val pokemons: List<BiomePokemon>,
)

fun BossPokemonResponse.toData(): BossPokemon =
    BossPokemon(
        tier = tier,
        pokemons = pokemons.toData(),
    )

fun List<BossPokemonResponse>.toData(): List<BossPokemon> =
    filter {
        it.pokemons.isNotEmpty()
    }.map(BossPokemonResponse::toData)
