package poke.rogue.helper.presentation.dex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.repository.PokemonListRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi

class PokemonListViewModel(
    pokemonListRepository: PokemonListRepository,
) : ViewModel(), PokeMonItemClickListener, PokemonQueryHandler {
    private val initialUiState =
        runBlocking {
            pokemonListRepository.pokemons().first().map(Pokemon::toUi)
        }

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val uiState: StateFlow<List<PokemonUiModel>> =
        searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                pokemonsQueryFlow(query, pokemonListRepository)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                initialUiState,
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

    private fun pokemonsQueryFlow(
        query: String,
        pokemonListRepository: PokemonListRepository,
    ): Flow<List<PokemonUiModel>> {
        if (query.isEmpty()) {
            return pokemonListRepository.pokemons().map(List<Pokemon>::toUi)
        }
        return pokemonListRepository.searchedPokemons(query).map(List<Pokemon>::toUi)
    }

    companion object {
        fun factory(pokemonListRepository: PokemonListRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                PokemonListViewModel(pokemonListRepository)
            }
    }
}
