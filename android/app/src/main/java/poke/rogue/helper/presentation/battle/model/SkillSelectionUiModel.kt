package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.data.model.BattleSkill

@Parcelize
data class SkillSelectionUiModel(
    val id: String,
    val name: String,
    val typeLogo: String,
    val categoryLogo: String,
) : Parcelable {
    companion object {
        val DUMMY = listOf<SkillSelectionUiModel>()
    }
}

fun BattleSkill.toUi(): SkillSelectionUiModel =
    SkillSelectionUiModel(
        id = id,
        name = name,
        typeLogo = typeLogo,
        categoryLogo = categoryLogo,
    )
