package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.R
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

fun MatchedResult.displayName(): String {
    return when (this) {
        MatchedResult.STRONG -> "강한"
        MatchedResult.WEAK -> "약한"
        MatchedResult.INEFFECTIVE -> "무효한"
        MatchedResult.NORMAL -> "대등한"
    }
}

fun MatchedResult.displayColor(): Int {
    return when (this) {
        MatchedResult.STRONG -> R.color.poke_red_20
        MatchedResult.WEAK -> R.color.poke_green_20
        MatchedResult.INEFFECTIVE -> R.color.poke_grey_60
        MatchedResult.NORMAL -> R.color.poke_white
    }
}

fun MatchedTypes.toUiModel(
    typeId: Int,
    isMyType: Boolean,
): MatchedTypesUiModel {
    val inputType = TypeUiModel.fromId(typeId) ?: throw IllegalArgumentException("Unknown type ID: $typeId")
    return MatchedTypesUiModel(
        selectedType = inputType,
        isMyType = isMyType,
        matchedResult = this.matchedResult.displayName(),
        matchedResultColorResId = this.matchedResult.displayColor(),
        matchedItem = this.types.map { it.toUi() },
    )
}

fun List<MatchedTypes>.sortedAndMappedToUiModel(
    selectedTypeId: Int,
    isMyType: Boolean,
): List<MatchedTypesUiModel> {
    return this.sortedBy { it.matchedResult }
        .map { it.toUiModel(selectedTypeId, isMyType) }
}
