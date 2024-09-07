package poke.rogue.helper.presentation.battle

import poke.rogue.helper.presentation.battle.model.SelectionMode

interface BattleNavigationHandler {
    fun navigateToSelection(selectionMode: SelectionMode)
}
