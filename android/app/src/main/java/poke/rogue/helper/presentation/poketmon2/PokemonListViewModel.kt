package poke.rogue.helper.presentation.poketmon2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel(), PokeMonItemClickListener {
    private val _uiState = MutableStateFlow(List(50) { PokemonUiModel.DUMMY })
    val uiState: StateFlow<List<PokemonUiModel>> = _uiState.asStateFlow()

    private val _navigateToDetailEvent = MutableSharedFlow<Long>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    override fun onClickPokemon(pokemonId: Int) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId.toLong())
        }
    }
}