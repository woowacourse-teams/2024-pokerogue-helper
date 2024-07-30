package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonDetailResponse(
    @SerialName("pokedexNumber")
    val dexNumber: Long,
    @SerialName("koName")
    val name: String,
    @SerialName("pokemonImage")
    val imageUrl: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
    @SerialName("pokemonAbilityResponses")
    val abilities: List<AbilityResponse>,
    val weight: Float,
    val height: Float,
    val totalStats: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)
