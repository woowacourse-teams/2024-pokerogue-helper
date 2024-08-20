package poke.rogue.helper.remote.dto.response.battle

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSkillResponse(
    val id: String,
    val name: String,
    val typeLogo: String,
    val categoryLogo: String,
    val power: Int,
    val accuracy: Int,
    val effect: String,
)
