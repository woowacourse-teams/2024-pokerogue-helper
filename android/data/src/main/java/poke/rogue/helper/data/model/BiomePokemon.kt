package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biomes.BiomePokemonResponse
import poke.rogue.helper.remote.dto.response.biomes.BossPokemonResponse
import poke.rogue.helper.remote.dto.response.biomes.GymPokemonResponse
import poke.rogue.helper.remote.dto.response.biomes.WildPokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class BiomePokemon(
    val name: String,
    val image: String,
    val types: List<Type>
)

fun BiomePokemonResponse.toData(): BiomePokemon =
    BiomePokemon(
        name = name,
        image = image,
        types = types.map(PokemonTypeResponse::toData)
    )

fun List<BiomePokemonResponse>.toData(): List<BiomePokemon> =
    map(BiomePokemonResponse::toData)

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
    map(BossPokemonResponse::toData)


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

