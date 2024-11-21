package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel1

interface TypeHandler {
    fun startSelection(selectorType: SelectorType)

    fun selectType(
        selectorType: SelectorType,
        selectedType: TypeUiModel1,
    )

    fun removeSelection(selectorType: SelectorType)

    fun removeAllSelections()
}
