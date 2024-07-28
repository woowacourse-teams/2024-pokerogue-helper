package poke.rogue.helper.presentation.type.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class MatchedTypesUiModel(
    val selectedType: TypeUiModel,
    val isMyType: Boolean,
    @StringRes val matchedResultDescriptionResId: Int,
    @ColorRes val matchedResultColorResId: Int,
    val matchedItem: List<TypeUiModel>,
)
