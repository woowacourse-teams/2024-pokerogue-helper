package poke.rogue.helper.presentation.type.mapper

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

enum class MatchedResultUiModel(
    @StringRes val descriptionRes: Int,
    @ColorRes val colorRes: Int,
) {
    STRONG(R.string.type_result_strong, R.color.poke_red_20),
    WEAK(R.string.type_result_weak, R.color.poke_green_20),
    INEFFECTIVE(R.string.type_result_ineffective, R.color.poke_grey_60),
    NORMAL(R.string.type_result_normal, R.color.poke_white),
    ;

    companion object {
        fun fromMatchedResult(matchedResult: MatchedResult): MatchedResultUiModel {
            return when (matchedResult) {
                MatchedResult.STRONG -> STRONG
                MatchedResult.WEAK -> WEAK
                MatchedResult.INEFFECTIVE -> INEFFECTIVE
                MatchedResult.NORMAL -> NORMAL
            }
        }
    }
}

fun MatchedTypes.toUi(
    typeId: Int,
    isMyType: Boolean,
): MatchedTypesUiModel {
    val inputTypeUi = TypeUiModel.fromId(typeId) ?: throw IllegalArgumentException("Unknown type ID: $typeId")
    val matchedResultUi = MatchedResultUiModel.fromMatchedResult(this.matchedResult)
    return MatchedTypesUiModel(
        selectedType = inputTypeUi,
        isMyType = isMyType,
        matchedResultDescriptionResId = matchedResultUi.descriptionRes,
        matchedResultColorResId = matchedResultUi.colorRes,
        matchedItem = this.types.map { it.toUi() },
    )
}

fun List<MatchedTypes>.sortedAndMappedToUi(
    selectedTypeId: Int,
    isMyType: Boolean,
): List<MatchedTypesUiModel> {
    return this.sortedBy { it.matchedResult }
        .map { it.toUi(selectedTypeId, isMyType) }
}
