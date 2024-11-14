package poke.rogue.helper.presentation.battle

sealed interface BattleSelectionUiState<out T : Any> {
    data class Selected<T : Any>(val content: T) : BattleSelectionUiState<T>

    data object Empty : BattleSelectionUiState<Nothing>
}

fun <T : Any> BattleSelectionUiState<T>.isSelected(): Boolean = this is BattleSelectionUiState.Selected

fun <T : Any> BattleSelectionUiState<T>.selectedData(): T? = (this as? BattleSelectionUiState.Selected)?.content
