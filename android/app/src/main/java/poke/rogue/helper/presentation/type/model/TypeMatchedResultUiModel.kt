package poke.rogue.helper.presentation.type.model

import androidx.annotation.DrawableRes

data class TypeMatchedResultUiModel(
    val typeName: String,
    @DrawableRes val typeIconResId: Int,
    val isMyType: Boolean,
    val matchedResult: String,
    val matchedItem: List<TypeUiModel>,
)
