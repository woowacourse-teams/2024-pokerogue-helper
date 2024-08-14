package poke.rogue.helper.presentation.dex.model

import androidx.annotation.DrawableRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.MoveCategory

data class MoveCategoryUiModel(
    val id: Long,
    val name: String,
    @DrawableRes val iconResId: Int,
)

fun MoveCategory.toUi(): MoveCategoryUiModel =
    MoveCategoryUiModel(
        id = id,
        name = name,
        iconResId =
            when (this) {
                MoveCategory.physicalMove -> R.drawable.ic_physical_attack_move
                MoveCategory.specialMove -> R.drawable.ic_special_attack_move
                MoveCategory.statusMove -> R.drawable.ic_change_status_move
                else -> throw IllegalArgumentException("Unknown move category: $this")
            },
    )
