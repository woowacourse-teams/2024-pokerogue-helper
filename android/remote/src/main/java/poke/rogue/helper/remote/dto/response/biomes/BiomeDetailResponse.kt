package poke.rogue.helper.remote.dto.response.biomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
class BiomeDetailResponse(
    val id: String,
    val name: String,
    val image: String,
    val wildPokemons: List<WildPokemon>,
    val bossPokemons: List<BossPokemon>,
    @SerialName("trainerPokemons")
    val gymPokemons: List<GymPokemon>,
    val map: List<Map>
)

@Serializable
data class biomePokemon(
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>
)

@Serializable
data class WildPokemon(
    val tier: String,
    val pokemons: List<biomePokemon>
)

@Serializable
data class BossPokemon(
    val tier: String,
    val pokemons: List<biomePokemon>
)


@Serializable
data class GymPokemon(
    @SerialName("trainerName")
    val gymLeaderName: String,
    @SerialName("trainerImage")
    val gymLeaderImage: String,
    @SerialName("trainerTypeLogos")
    val gymLeaderLogos: List<String>,
    val pokemons: List<biomePokemon>
)

@Serializable
data class Map(
    val id: String,
    val name: String,
    val image: String,
    val probability: Double,
)
