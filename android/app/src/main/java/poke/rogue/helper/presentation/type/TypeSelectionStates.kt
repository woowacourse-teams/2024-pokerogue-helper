package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel1

data class TypeSelectionStates(
    val myType: TypeSelectionUiState = TypeSelectionUiState.Empty,
    val opponentType1: TypeSelectionUiState = TypeSelectionUiState.Empty,
    val opponentType2: TypeSelectionUiState = TypeSelectionUiState.Empty,
) {
    val selectedOpponents: List<TypeSelectionUiState.Selected>
        get() =
            listOf(
                opponentType1,
                opponentType2,
            ).filterIsInstance<TypeSelectionUiState.Selected>()

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

    val isAllEmpty: Boolean
        get() = !isMyTypeSelected && !isOpponent1Selected && !isOpponent2Selected

    fun disabledTypeItems(selectorType: SelectorType): Set<TypeUiModel1> {
        return when (selectorType) {
            SelectorType.MINE -> emptySet()
            SelectorType.OPPONENT1 -> opponentType2.selectedTypes()
            SelectorType.OPPONENT2 -> opponentType1.selectedTypes()
        }
    }
}

private fun TypeSelectionUiState.isSelected(): Boolean = this is TypeSelectionUiState.Selected

private fun TypeSelectionUiState.isEmpty(): Boolean = this is TypeSelectionUiState.Empty

private fun TypeSelectionUiState.selectedTypes(): Set<TypeUiModel1> {
    return when (this) {
        is TypeSelectionUiState.Selected -> setOf(selectedType)
        else -> emptySet()
    }
}
