package poke.rogue.helper.presentation.type.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.MatchedResult

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

object MatchedTypesUiModelComparator : Comparator<MatchedTypesUiModel> {
    private val order =
        listOf(
            MatchedResultUiModel.STRONG,
            MatchedResultUiModel.WEAK,
            MatchedResultUiModel.INEFFECTIVE,
            MatchedResultUiModel.NORMAL,
        )

    override fun compare(
        a: MatchedTypesUiModel,
        b: MatchedTypesUiModel,
    ): Int {
        val indexA = order.indexOf(a.matchedResultUi)
        val indexB = order.indexOf(b.matchedResultUi)
        return indexA.compareTo(indexB)
    }
}
