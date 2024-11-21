package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.TypeUiModel1

sealed interface TypeSelectionUiState {
    data class Selected(val selectedType: TypeUiModel1) : TypeSelectionUiState

    data object Empty : TypeSelectionUiState
}
