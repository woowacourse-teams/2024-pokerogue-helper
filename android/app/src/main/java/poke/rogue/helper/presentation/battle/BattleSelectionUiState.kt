package poke.rogue.helper.presentation.battle

sealed interface BattleSelectionUiState<out T : Any> {
    data class Selected<T : Any>(val selected: T) : BattleSelectionUiState<T>

    data object Empty : BattleSelectionUiState<Nothing>

    fun selectedData(): T? = (this as? Selected)?.selected
}

fun <T : Any> BattleSelectionUiState<T>.isSelected(): Boolean = this is BattleSelectionUiState.Selected
