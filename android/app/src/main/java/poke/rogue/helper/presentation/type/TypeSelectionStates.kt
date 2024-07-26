package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

data class TypeSelectionStates(
    val myType: TypeSelectionUiState = TypeSelectionUiState.Empty,
    val opponentType1: TypeSelectionUiState = TypeSelectionUiState.Empty,
    val opponentType2: TypeSelectionUiState = TypeSelectionUiState.Empty,
) {
    val isMyTypeSelected: Boolean
        get() = myType.isSelected()

    val isOpponent1Selected: Boolean
        get() = opponentType1.isSelected()

    val isOpponent2Selected: Boolean
        get() = opponentType2.isSelected()

    val isMyTypeEmptyAndAnyOpponentSelected: Boolean
        get() = myType.isEmpty() && (isOpponent1Selected || isOpponent2Selected)

    val isMyTypeSelectedAndOpponentsEmpty: Boolean
        get() = isMyTypeSelected && opponentType1.isEmpty() && opponentType2.isEmpty()

    val isMyTypeSelectedAndAnyOpponentSelected: Boolean
        get() = isMyTypeSelected && (isOpponent1Selected || isOpponent2Selected)

    val areAllTypesEmpty: Boolean
        get() = !isMyTypeSelected && !isOpponent1Selected && !isOpponent2Selected

    fun disabledTypeItemSet(selectorType: SelectorType): Set<TypeUiModel> {
        return when (selectorType) {
            SelectorType.MINE -> emptySet()
            SelectorType.OPPONENT1 -> opponentType2.selectedTypeSet()
            SelectorType.OPPONENT2 -> opponentType1.selectedTypeSet()
        }
    }
}

private fun TypeSelectionUiState.isSelected(): Boolean = this is TypeSelectionUiState.Selected

private fun TypeSelectionUiState.isEmpty(): Boolean = this is TypeSelectionUiState.Empty

private fun TypeSelectionUiState.selectedTypeSet(): Set<TypeUiModel> {
    return (this as? TypeSelectionUiState.Selected)?.let { setOf(it.selectedType) } ?: emptySet()
}
