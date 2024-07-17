package poke.rogue.helper.presentation.dex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class PokemonListViewModel : ViewModel(), PokeMonItemClickListener {
    private val _uiState =
        MutableStateFlow(
            PokemonUiModel.dummys(50),
        )

    val uiState: StateFlow<List<PokemonUiModel>> = _uiState.asStateFlow()

    private val _navigateToDetailEvent = MutableSharedFlow<Long>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    override fun onClickPokemon(pokemonId: Long) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId)
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory = BaseViewModelFactory { PokemonListViewModel() }
    }
}
