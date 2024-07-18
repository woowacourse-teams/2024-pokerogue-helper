package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.TypeUiModel

sealed interface TypeSelectionUiState {
    data class Selected(val selectedType: TypeUiModel) : TypeSelectionUiState

    data object Idle : TypeSelectionUiState
}
