package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

interface TypeHandler {
    fun showSelection(selectorType: SelectorType)

    fun applySelection(
        selectorType: SelectorType,
        selectedType: TypeUiModel,
    )

    fun removeSelection(selectorType: SelectorType)

    fun removeAllSelections()
}
