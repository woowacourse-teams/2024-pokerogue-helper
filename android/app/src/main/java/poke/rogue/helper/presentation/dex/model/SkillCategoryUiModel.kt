package poke.rogue.helper.presentation.dex.model

import androidx.annotation.DrawableRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.SkillCategory

data class SkillCategoryUiModel(
    val id: String,
    val name: String,
    @DrawableRes val iconResId: Int,
)

fun SkillCategory.toUi(): SkillCategoryUiModel =
    SkillCategoryUiModel(
        id = id,
        name = name,
        iconResId =
            when (this) {
                SkillCategory.physicalAttackSkill -> R.drawable.ic_physical_attack_skill
                SkillCategory.specialAttackSkill -> R.drawable.ic_special_attack_skill
                SkillCategory.changeStatusSkill -> R.drawable.ic_change_status_skill
                else -> throw IllegalArgumentException("Unknown skill category: $this")
            },
    )
