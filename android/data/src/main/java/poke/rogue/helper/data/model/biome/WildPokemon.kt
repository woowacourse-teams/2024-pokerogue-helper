package poke.rogue.helper.data.model.biome

import poke.rogue.helper.remote.dto.response.biomes.WildPokemonResponse

data class WildPokemon(
    val tier: String,
    val pokemons: List<BiomePokemon>,
)

fun WildPokemonResponse.toData(): WildPokemon =
    WildPokemon(
        tier = tier,
        pokemons = pokemons.toData(),
    )

fun List<WildPokemonResponse>.toData(): List<WildPokemon> =
    map(WildPokemonResponse::toData)
