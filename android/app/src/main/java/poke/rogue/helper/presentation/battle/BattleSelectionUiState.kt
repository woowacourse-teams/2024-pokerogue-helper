package poke.rogue.helper.presentation.battle

sealed interface BattleSelectionUiState<out T : Any> {
    data class Selected<T : Any>(val selected: T, val hasSelectionChanged: Boolean = false) :
        BattleSelectionUiState<T>

    data object Empty : BattleSelectionUiState<Nothing>
}

fun <T : Any> BattleSelectionUiState<T>.isSelected(): Boolean = this is BattleSelectionUiState.Selected

fun <T : Any> BattleSelectionUiState<T>.selectedData(): T? = (this as? BattleSelectionUiState.Selected)?.selected

fun <T : Any> BattleSelectionUiState<T>.hasSelectedChanged(): Boolean =
    (this as? BattleSelectionUiState.Selected)?.hasSelectionChanged == true
