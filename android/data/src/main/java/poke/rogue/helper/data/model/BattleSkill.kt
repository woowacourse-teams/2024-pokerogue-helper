package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.battle.PokemonSkillResponse

data class BattleSkill(
    val id: String,
    val name: String,
    val typeLogo: String,
    val categoryLogo: String,
    val power: Int,
    val accuracy: Int,
    val effect: String,
)

fun PokemonSkillResponse.toData(): BattleSkill =
    BattleSkill(
        id = id,
        name = name,
        typeLogo = typeLogo,
        categoryLogo = categoryLogo,
        power = power,
        accuracy = accuracy,
        effect = effect,
    )
