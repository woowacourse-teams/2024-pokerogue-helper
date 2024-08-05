package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.data.repository.PokemonDetailRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class PokemonDetailViewModel(private val pokemonDetailRepository: PokemonDetailRepository) :
    ViewModel(),
    PokemonDetailNavigateHandler {
    private val _uiState: MutableStateFlow<PokemonDetailUiState> =
        MutableStateFlow(PokemonDetailUiState.IsLoading)
    val uiState = _uiState.asStateFlow()

    private val _navigationToDetailEvent = MutableSharedFlow<Long>()
    val navigationToDetailEvent: SharedFlow<Long> = _navigationToDetailEvent.asSharedFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        viewModelScope.launch {
            requireNotNull(pokemonId) { "Pokemon ID must not be null" }
            _uiState.value = pokemonDetailRepository.pokemonDetail(pokemonId).toUi()
        }
    }

    override fun navigateToAbilityDetail(abilityId: Long) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(abilityId)
        }
    }

    companion object {
        fun factory(pokemonDetailRepository: PokemonDetailRepository): ViewModelProvider.Factory =
            BaseViewModelFactory { PokemonDetailViewModel(pokemonDetailRepository) }
    }
}
