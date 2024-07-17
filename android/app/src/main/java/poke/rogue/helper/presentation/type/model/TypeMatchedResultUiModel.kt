package poke.rogue.helper.presentation.type.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class TypeMatchedResultUiModel(
    val typeName: String,
    @DrawableRes val typeIconResId: Int,
    val isMyType: Boolean,
    val matchedResult: String,
    @ColorRes val matchedResultColorResId: Int,
    val matchedItem: List<TypeUiModel>,
)
