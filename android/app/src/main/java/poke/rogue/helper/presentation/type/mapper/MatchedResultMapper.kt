package poke.rogue.helper.presentation.type.mapper

import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.toUi

fun List<MatchedTypes>.sortedAndMappedToUi(
    selectedTypeId: Int,
    isMyType: Boolean,
): List<MatchedTypesUiModel> {
    return this.sortedBy { it.matchedResult }
        .map { it.toUi(selectedTypeId, isMyType) }
}
