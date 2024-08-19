package poke.rogue.helper.data.model.biome

import poke.rogue.helper.remote.dto.response.biomes.GymPokemonResponse

data class GymPokemon(
    val gymLeaderName: String,
    val gymLeaderImage: String,
    val gymLeaderLogos: List<String>,
    val pokemons: List<BiomePokemon>,
)

fun GymPokemonResponse.toData(): GymPokemon =
    GymPokemon(
        gymLeaderName = gymLeaderName,
        gymLeaderImage = gymLeaderImage,
        gymLeaderLogos = gymLeaderLogos,
        pokemons = pokemons.toData(),
    )

fun List<GymPokemonResponse>.toData(): List<GymPokemon> =
    map(GymPokemonResponse::toData)
