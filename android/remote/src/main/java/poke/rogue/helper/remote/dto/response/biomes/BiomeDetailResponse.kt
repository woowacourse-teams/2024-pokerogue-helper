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
    @SerialName("nextBiomes")
    val nextBiomes: List<NextBiomesResponse>,
)

@Serializable
data class BiomePokemonResponse(
    val id: String,
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
)

@Serializable
data class WildPokemonResponse(
    val tier: String,
    val pokemons: List<BiomePokemonResponse>,
)

@Serializable
data class BossPokemonResponse(
    val tier: String,
    val pokemons: List<BiomePokemonResponse>,
)

@Serializable
data class GymPokemonResponse(
    @SerialName("trainerName")
    val gymLeaderName: String,
    @SerialName("trainerImage")
    val gymLeaderImage: String,
    @SerialName("trainerTypeResponses")
    val gymLeaderTypeLogos: List<PokemonTypeResponse>,
    val pokemons: List<BiomePokemonResponse>,
)

@Serializable
data class NextBiomesResponse(
    val id: String,
    val name: String,
    val image: String,
    @SerialName("pokemonTypeResponses")
    val pokemonTypes: List<PokemonTypeResponse>,
    @SerialName("trainerTypeResponses")
    val gymLeaderTypes: List<PokemonTypeResponse>,
    @SerialName("percent")
    val probability: Double,
)
