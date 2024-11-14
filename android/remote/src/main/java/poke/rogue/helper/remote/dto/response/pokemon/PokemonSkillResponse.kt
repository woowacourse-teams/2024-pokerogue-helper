package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSkillResponse(
    val id: String,
    val name: String,
    val level: Int,
    val power: Int,
    val accuracy: Int,
    val type: String,
    val typeLogo: String,
    val category: String,
    val categoryLogo: String,
)
