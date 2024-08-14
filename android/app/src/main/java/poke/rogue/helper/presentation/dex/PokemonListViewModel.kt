package poke.rogue.helper.presentation.dex

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.PokeException
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi

class PokemonListViewModel(
    private val pokemonListRepository: DexRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), PokemonListNavigateHandler, PokemonQueryHandler {
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<List<PokemonUiModel>> =
        merge(refreshEvent.map { "" }, searchQuery)
            .onStart {
                if (isEmpty.value) {
                    _isLoading.value = true
                }
            }
            .debounce(300L)
            .mapLatest { query ->
                queriedPokemons(query)
            }
            .stateIn(
                viewModelScope + errorHandler,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val isEmpty: StateFlow<Boolean> =
        uiState.map { it.isEmpty() && !isLoading.value }
            .stateIn(
                viewModelScope + errorHandler,
                SharingStarted.WhileSubscribed(5000),
                true,
            )

    private val _navigateToDetailEvent = MutableSharedFlow<Long>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    private suspend fun queriedPokemons(query: String): List<PokemonUiModel> {
        return try {
            if (query.isEmpty()) {
                pokemonListRepository.pokemons().map(Pokemon::toUi)
            } else {
                pokemonListRepository.filteredPokemons(query).map(Pokemon::toUi)
            }
        } catch (e: PokeException) {
            handlePokemonError(e)
            emptyList()
        } finally {
            _isLoading.value = false
        }
    }

    override fun navigateToPokemonDetail(pokemonId: Long) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId)
        }
    }

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.value = name
        }
    }

    companion object {
        fun factory(pokemonListRepository: DexRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                PokemonListViewModel(pokemonListRepository)
            }
    }
}
