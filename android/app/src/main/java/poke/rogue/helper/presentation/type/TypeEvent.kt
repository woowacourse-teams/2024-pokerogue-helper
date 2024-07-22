package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType

sealed interface TypeEvent {
    data class ShowSelection(val selectorType: SelectorType) : TypeEvent

    data object HideSelection : TypeEvent
}
