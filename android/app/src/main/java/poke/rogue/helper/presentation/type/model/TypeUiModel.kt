package poke.rogue.helper.presentation.type.model

import androidx.annotation.DrawableRes

data class TypeUiModel(
    val id: Int,
    val name: String,
    @DrawableRes val iconResId: Int,
)
