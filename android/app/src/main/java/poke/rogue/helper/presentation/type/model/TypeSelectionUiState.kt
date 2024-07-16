package poke.rogue.helper.presentation.type.model

sealed interface TypeSelectionUiState {
    data class Selected(val selectedType: TypeUiModel) : TypeSelectionUiState

    data object Idle : TypeSelectionUiState
}
