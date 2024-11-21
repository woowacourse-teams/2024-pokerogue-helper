package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel1

sealed interface TypeEvent {
    data class ShowSelection(val selectorType: SelectorType, val disabledTypes: Set<TypeUiModel1>) : TypeEvent

    data object HideSelection : TypeEvent
}
