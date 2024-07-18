package poke.rogue.helper.remote.dto.response.poke

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonDetailResponse(
    val pokedexNumber: Long,
    val name: String,
    val pokemonImage: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
    @SerialName("pokemonAbilityNames")
    val abilityNames: List<String>,
    val weight: Int,
    val height: Int,
    val baseTotal: Int,
    val baseHp: Int,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseSpecialAttack: Int,
    val baseSpecialDefense: Int,
    val baseSpeed: Int
)
