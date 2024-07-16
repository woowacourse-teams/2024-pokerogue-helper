package poke.rogue.helper.presentation.type

import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel

interface TypeHandler {
    fun selectType(
        selectorType: SelectorType,
        selectedType: TypeUiModel,
    )

    fun deleteMyType()

    fun deleteOpponent1Type()

    fun deleteOpponent2Type()
}
