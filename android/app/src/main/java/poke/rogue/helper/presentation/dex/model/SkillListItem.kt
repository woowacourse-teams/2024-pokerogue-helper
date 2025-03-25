package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.model.SkillCategory
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

sealed class SkillListItem {
    data object Header : SkillListItem()

    data class Skill(
        val id: String,
        val name: String,
        val level: Int,
        val power: String,
        val type: TypeUiModel,
        val accuracy: String,
        val category: SkillCategory,
    ) : SkillListItem() {
        companion object {
            const val NO_POWER = "-"
            const val NO_ACCURACY = "-"
        }
    }
}

fun PokemonSkill.toUi(): SkillListItem.Skill =
    SkillListItem.Skill(
        id = id,
        name = name,
        level = level,
        power = if (power == PokemonSkill.NO_POWER_VALUE) SkillListItem.Skill.NO_POWER else power.toString(),
        type = type.toUi(),
        accuracy = if (accuracy == PokemonSkill.NO_ACCURACY_VALUE) SkillListItem.Skill.NO_ACCURACY else accuracy.toString(),
        category = category,
    )

fun List<PokemonSkill>.toUi(): List<SkillListItem.Skill> = map(PokemonSkill::toUi)
