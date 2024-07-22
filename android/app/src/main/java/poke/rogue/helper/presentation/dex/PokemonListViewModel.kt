package poke.rogue.helper.presentation.dex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.repository.PokemonListRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import timber.log.Timber

class PokemonListViewModel(
    pokemonListRepository: PokemonListRepository,
) : ViewModel(), PokeMonItemClickListener {
    private val _uiState =
        MutableStateFlow(
            pokemonListRepository.pokemons().map(Pokemon::toUi),
        )

    private val _searchQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    @FlowPreview
    val uiState: StateFlow<List<PokemonUiModel>> =
        _searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                if (query.isEmpty()) {
                    _uiState
                } else {
                    pokemonListRepository.searchPokemons(query).map { list ->
                        list.map(Pokemon::toUi)
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                emptyList(),
            ).also {
                Timber.d("result: $it")
            }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _navigateToDetailEvent = MutableSharedFlow<Long>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    override fun onClickPokemon(pokemonId: Long) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId)
        }
    }

    companion object {
        fun factory(pokemonListRepository: PokemonListRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                PokemonListViewModel(pokemonListRepository)
            }
    }
}
