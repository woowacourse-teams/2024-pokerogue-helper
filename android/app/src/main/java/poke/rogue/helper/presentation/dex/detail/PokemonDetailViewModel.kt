package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.data.repository.PokemonDetailRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class PokemonDetailViewModel(private val pokemonDetailRepository: PokemonDetailRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<PokemonDetailUiState> = MutableStateFlow(PokemonDetailUiState.IsLoading)
    val uiState = _uiState.asStateFlow()

    suspend fun updatePokemonDetail(pokemonId: Long?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        _uiState.value = pokemonDetailRepository.pokemonDetail(pokemonId).toUi()
    }

    companion object {
        fun factory(pokemonDetailRepository: PokemonDetailRepository): ViewModelProvider.Factory =
            BaseViewModelFactory { PokemonDetailViewModel(pokemonDetailRepository) }
    }
}
