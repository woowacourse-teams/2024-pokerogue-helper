package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.ability.PokemonAbilityResponse
import poke.rogue.helper.remote.dto.response.biom.PokemonBiomeResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonDetailResponse(
    val id: String,
    @SerialName("pokedexNumber")
    val dexNumber: Long,
    val name: String,
    @SerialName("pokemonImage")
    val imageUrl: String,
    @SerialName("pokemonTypeResponses")
    val types: List<PokemonTypeResponse>,
    @SerialName("pokemonAbilityResponses")
    val abilities: List<PokemonAbilityResponse>,
    val totalStats: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val evolutions: EvolutionsResponse,
    @SerialName("eggMoveResponses")
    val eggSkills: List<PokemonSkillResponse>,
    @SerialName("moves")
    val selfLearnSkills: List<PokemonSkillResponse>,
    val weight: Float,
    val height: Float,
    val biomes: List<PokemonBiomeResponse>,
    val mythical: Boolean,
    val subLegendary: Boolean,
    val legendary: Boolean,
    val canChangeForm: Boolean,
)
