package poke.rogue.helper.presentation.biome

interface BiomeUiState<out T : Any> {
    data object Loading : BiomeUiState<Nothing>

    data class Success<out T : Any>(val data: T) : BiomeUiState<T>
}
