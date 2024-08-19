package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonSkillUiModel(
    val id: String,
    val name: String,
    val level: Int,
    val power: String,
    val type: TypeUiModel,
    val accuracy: Int,
    val category: SkillCategoryUiModel,
) {
    companion object {
        const val NO_POWER = "-"
    }
}

fun PokemonSkill.toUi(): PokemonSkillUiModel =
    PokemonSkillUiModel(
        id = id.toString(),
        name = name,
        level = level,
        power = if (power == PokemonSkill.NO_POWER_VALUE) PokemonSkillUiModel.NO_POWER else power.toString(),
        type = type.toUi(),
        accuracy = accuracy,
        category = category.toUi(),
    )

fun List<PokemonSkill>.toUi(): List<PokemonSkillUiModel> = map(PokemonSkill::toUi)
