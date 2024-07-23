package poke.rogue.helper.presentation.dex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.repository.PokemonListRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi

class PokemonListViewModel(
    private val pokemonListRepository: PokemonListRepository,
) : ViewModel(), PokeMonItemClickListener, PokemonQueryHandler {
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val uiState: StateFlow<List<PokemonUiModel>> =
        searchQuery
            .debounce(300)
            .map { query ->
                queriedPokemons(query)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                pokemonListRepository.pokemons2().map(Pokemon::toUi),
            )

    private val _navigateToDetailEvent = MutableSharedFlow<Long>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    override fun onClickPokemon(pokemonId: Long) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId)
        }
    }

    override fun onQueryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }

    private fun queriedPokemons(query: String): List<PokemonUiModel> {
        if (query.isEmpty()) {
            return pokemonListRepository.pokemons2().map(Pokemon::toUi)
        }
        return pokemonListRepository.pokemons(query).map(Pokemon::toUi)
    }

    companion object {
        fun factory(pokemonListRepository: PokemonListRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                PokemonListViewModel(pokemonListRepository)
            }
    }
}
