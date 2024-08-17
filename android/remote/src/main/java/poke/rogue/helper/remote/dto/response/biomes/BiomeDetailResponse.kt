package poke.rogue.helper.remote.dto.response.biomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
class BiomeDetailResponse(
    val id: String,
    val name: String,
    val image: String,
    val wildPokemons: List<WildPokemonResponse>,
    val bossPokemons: List<BossPokemonResponse>,
    @SerialName("trainerPokemons")
    val gymPokemons: List<GymPokemonResponse>,
    val map: List<MapResponse>
)

@Serializable
data class BiomePokemonResponse(
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>
)

@Serializable
data class WildPokemonResponse(
    val tier: String,
    val pokemons: List<BiomePokemonResponse>
)

@Serializable
data class BossPokemonResponse(
    val tier: String,
    val pokemons: List<BiomePokemonResponse>
)

@Serializable
data class GymPokemonResponse(
    @SerialName("trainerName")
    val gymLeaderName: String,
    @SerialName("trainerImage")
    val gymLeaderImage: String,
    @SerialName("trainerTypeLogos")
    val gymLeaderLogos: List<String>,
    val pokemons: List<BiomePokemonResponse>
)

@Serializable
data class MapResponse(
    val id: String,
    val name: String,
    val image: String,
    val probability: Double,
)
