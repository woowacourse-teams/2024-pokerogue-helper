package poke.rogue.helper.presentation.ability

sealed interface AbilityUiState<out T : Any> {
    data object Loading : AbilityUiState<Nothing>

    data class Success<out T : Any>(val data: T) : AbilityUiState<T>
}
