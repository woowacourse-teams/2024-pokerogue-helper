package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.R
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel.Companion.toUiModel

fun MatchedResult.displayName(): String {
    return when (this) {
        MatchedResult.STRONG -> "강한"
        MatchedResult.WEAK -> "약한"
        MatchedResult.INEFFECTIVE -> "무효한"
        MatchedResult.NORMAL -> throw IllegalStateException("")
    }
}

fun MatchedResult.displayColor(): Int {
    return when (this) {
        MatchedResult.STRONG -> R.color.poke_red_20
        MatchedResult.WEAK -> R.color.poke_green_20
        MatchedResult.INEFFECTIVE -> R.color.poke_grey_80
        MatchedResult.NORMAL -> throw IllegalStateException("")
    }
}

fun MatchedTypes.toUiModel(
    typeId: Int,
    isMyType: Boolean,
): MatchedTypesUiModel {
    val inputType = TypeUiModel.fromId(typeId) ?: throw IllegalArgumentException("Unknown type ID: $typeId")
    return MatchedTypesUiModel(
        typeName = inputType.typeName,
        typeIconResId = inputType.typeIconResId,
        isMyType = isMyType,
        matchedResult = this.matchedResult.displayName(),
        matchedResultColorResId = this.matchedResult.displayColor(),
        matchedItem = this.types.map { it.toUiModel() },
    )
}
