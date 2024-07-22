package poke.rogue.helper.presentation.type.model

import androidx.annotation.ColorRes

data class MatchedTypesUiModel(
    val selectedType: TypeUiModel,
    val isMyType: Boolean,
    val matchedResult: String,
    @ColorRes val matchedResultColorResId: Int,
    val matchedItem: List<TypeUiModel>,
)
