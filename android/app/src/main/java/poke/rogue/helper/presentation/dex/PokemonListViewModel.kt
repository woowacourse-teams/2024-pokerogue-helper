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
import poke.rogue.helper.data.datasource.FakePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi

class PokemonListViewModel(
    pokemonListDataSource: FakePokemonListDataSource,
) : ViewModel(), PokeMonItemClickListener {
    private val _uiState =
        MutableStateFlow(
            pokemonListDataSource.pokemons().map(Pokemon::toUi),
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
        fun factory(pokemonListDataSource: FakePokemonListDataSource): ViewModelProvider.Factory =
            BaseViewModelFactory {
                PokemonListViewModel(pokemonListDataSource)
            }
    }
}
