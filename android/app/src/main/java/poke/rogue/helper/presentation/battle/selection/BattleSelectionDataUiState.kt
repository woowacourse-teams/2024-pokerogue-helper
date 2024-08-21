package poke.rogue.helper.presentation.battle.selection

sealed interface BattleSelectionDataUiState<out T : Any> {
    data object Loading : BattleSelectionDataUiState<Nothing>

    class Success<out T : Any>(val data: T) : BattleSelectionDataUiState<T>
}
