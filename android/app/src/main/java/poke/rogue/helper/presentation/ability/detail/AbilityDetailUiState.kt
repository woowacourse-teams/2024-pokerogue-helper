package poke.rogue.helper.presentation.ability.detail

interface AbilityDetailUiState<out T : Any> {
    data object Loading : AbilityDetailUiState<Nothing>

    data object Empty : AbilityDetailUiState<Nothing>

    data class Success<out T : Any>(val data: T) : AbilityDetailUiState<T>
}
