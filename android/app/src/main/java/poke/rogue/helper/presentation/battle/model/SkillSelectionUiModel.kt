package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.type.model.toUi

@Parcelize
data class SkillSelectionUiModel(
    val id: String,
    val name: String,
    val typeLogo: TypeUiModel1,
    val categoryLogo: String,
) : Parcelable

fun BattleSkill.toUi(): SkillSelectionUiModel =
    SkillSelectionUiModel(
        id = id,
        name = name,
        typeLogo = type.toUi(),
        categoryLogo = categoryLogo,
    )
