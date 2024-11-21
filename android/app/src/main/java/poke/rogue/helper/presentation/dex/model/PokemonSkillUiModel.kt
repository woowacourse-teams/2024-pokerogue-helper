package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.model.SkillCategory
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonSkillUiModel(
    val id: String,
    val name: String,
    val level: Int,
    val power: String,
    val type: TypeUiModel1,
    val accuracy: String,
    val category: SkillCategory,
) {
    companion object {
        const val NO_POWER = "-"
        const val NO_ACCURACY = "-"
    }
}

fun PokemonSkill.toUi(): PokemonSkillUiModel =
    PokemonSkillUiModel(
        id = id,
        name = name,
        level = level,
        power = if (power == PokemonSkill.NO_POWER_VALUE) PokemonSkillUiModel.NO_POWER else power.toString(),
        type = type.toUi(),
        accuracy = if (accuracy == PokemonSkill.NO_ACCURACY_VALUE) PokemonSkillUiModel.NO_ACCURACY else accuracy.toString(),
        category = category,
    )

fun List<PokemonSkill>.toUi(): List<PokemonSkillUiModel> = map(PokemonSkill::toUi)
