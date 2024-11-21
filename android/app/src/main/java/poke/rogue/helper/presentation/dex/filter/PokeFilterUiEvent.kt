package poke.rogue.helper.presentation.dex.filter

import poke.rogue.helper.presentation.type.model.TypeUiModel1

sealed interface PokeFilterUiEvent {
    data object IDLE : PokeFilterUiEvent

    data class ApplyFiltering(
        val selectedTypes: List<TypeUiModel1>,
        val generation: PokeGenerationUiModel,
    ) : PokeFilterUiEvent

    data object CloseFilter : PokeFilterUiEvent
}
