package poke.rogue.helper.presentation.type.model

import poke.rogue.helper.data.model.MatchedTypes

data class MatchedTypesUiModel(
    val selectedType: TypeUiModel,
    val isMyType: Boolean,
    val matchedResultUi: MatchedResultUiModel,
    val matchedItem: List<TypeUiModel>,
)

fun MatchedTypes.toUi(
    typeId: Int,
    isMyType: Boolean,
): MatchedTypesUiModel {
    val inputTypeUi = TypeUiModel.fromId(typeId)
    val matchedResultUi = MatchedResultUiModel.fromMatchedResult(this.matchedResult)
    return MatchedTypesUiModel(
        selectedType = inputTypeUi,
        isMyType = isMyType,
        matchedResultUi = matchedResultUi,
        matchedItem = this.types.map { it.toUi() },
    )
}

fun List<MatchedTypes>.toUi(
    selectedTypeId: Int,
    isMyType: Boolean,
): List<MatchedTypesUiModel> = this.map { it.toUi(selectedTypeId, isMyType) }
