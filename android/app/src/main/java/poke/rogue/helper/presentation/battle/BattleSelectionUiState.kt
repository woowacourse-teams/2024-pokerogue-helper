package poke.rogue.helper.presentation.battle

sealed interface BattleSelectionUiState<out T : Any> {
    data class Selected<T : Any>(val selected: T) : BattleSelectionUiState<T>

    data object Empty : BattleSelectionUiState<Nothing>
}
