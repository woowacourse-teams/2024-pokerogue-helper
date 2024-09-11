package poke.rogue.helper.data.model.biome

import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.biomes.GymPokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class GymPokemon(
    val gymLeaderName: String,
    val gymLeaderImage: String,
    val gymLeaderTypeLogos: List<Type>,
    val pokemons: List<BiomePokemon>,
)

fun GymPokemonResponse.toData(): GymPokemon =
    GymPokemon(
        gymLeaderName = gymLeaderName,
        gymLeaderImage = gymLeaderImage,
        gymLeaderTypeLogos = gymLeaderTypeLogos.map(PokemonTypeResponse::toData),
        pokemons = pokemons.toData(),
    )

fun List<GymPokemonResponse>.toData(): List<GymPokemon> = map(GymPokemonResponse::toData)
