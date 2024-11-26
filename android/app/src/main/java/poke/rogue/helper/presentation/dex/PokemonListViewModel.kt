package poke.rogue.helper.presentation.dex

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.PokeException
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.filter.PokeGenerationUiModel
import poke.rogue.helper.presentation.dex.filter.toDataOrNull
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.toUi
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel
import poke.rogue.helper.presentation.dex.sort.toData
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toData

class PokemonListViewModel(
    private val pokemonRepository: DexRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), PokemonListNavigateHandler, PokemonQueryHandler {
    private val searchQuery = MutableStateFlow("")
    private val pokeFilter =
        MutableStateFlow<PokeFilterUiModel>(
            PokeFilterUiModel(
                emptyList(),
                PokeGenerationUiModel.ALL,
            ),
        )
    private val pokeSort = MutableStateFlow(PokemonSortUiModel.ByDexNumber)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val uiState: StateFlow<PokemonListUiState> =
        merge(refreshEvent.map { "" }, searchQuery)
            .onStart {
                if (isEmpty.value) {
                    _isLoading.value = true
                }
            }
            .debounce(300L)
            .flatMapLatest { query ->
                combine(pokeSort, pokeFilter) { sort, filter ->
                    PokemonListUiState(
                        pokemons =
                            queriedPokemons(
                                query = query,
                                types = filter.selectedTypes,
                                generation = filter.selectedGeneration,
                                sort = sort,
                            ),
                        sort = sort,
                        filteredTypes = filter.selectedTypes,
                        filteredGeneration = filter.selectedGeneration,
                    )
                }
            }.stateIn(
                viewModelScope + errorHandler,
                SharingStarted.WhileSubscribed(5000),
                PokemonListUiState(),
            )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val isEmpty: StateFlow<Boolean> =
        uiState.map { it.pokemons.isEmpty() && !isLoading.value }
            .stateIn(
                viewModelScope + errorHandler,
                SharingStarted.WhileSubscribed(5000),
                true,
            )

    private val _navigateToDetailEvent = MutableSharedFlow<String>()
    val navigateToDetailEvent = _navigateToDetailEvent.asSharedFlow()

    private suspend fun queriedPokemons(
        query: String,
        types: List<TypeUiModel>,
        generation: PokeGenerationUiModel,
        sort: PokemonSortUiModel,
    ): List<PokemonUiModel> {
        return try {
            val filteredTypes = types.map { PokemonFilter.ByType(it.toData()) }
            val filteredGenerations =
                listOfNotNull(generation.toDataOrNull()).map { PokemonFilter.ByGeneration(it) }
            pokemonRepository.filteredPokemons(
                query,
                sort.toData(),
                filteredTypes + filteredGenerations,
            ).map {
                it.toUi().copy(sortUiModel = sort)
            }
        } catch (e: PokeException) {
            handlePokemonError(e)
            emptyList()
        } finally {
            _isLoading.value = false
        }
    }

    override fun navigateToPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            _navigateToDetailEvent.emit(pokemonId)
        }
    }

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.value = name
        }
    }

    fun filterPokemon(filter: PokeFilterUiModel) {
        viewModelScope.launch {
            pokeFilter.value = filter
        }
        analyticsLogger().logPokemonFilter(filter)
    }

    fun sortPokemon(sort: PokemonSortUiModel) {
        viewModelScope.launch {
            pokeSort.value = sort
        }
        analyticsLogger().logPokemonSort(sort)
    }
}

data class PokemonListUiState(
    val pokemons: List<PokemonUiModel> = emptyList(),
    val sort: PokemonSortUiModel = PokemonSortUiModel.ByDexNumber,
    val filteredTypes: List<TypeUiModel> = emptyList(),
    val filteredGeneration: PokeGenerationUiModel = PokeGenerationUiModel.ALL,
) {
    val isSorted get() = sort != PokemonSortUiModel.ByDexNumber
    val isFiltered get() = filteredTypes.isNotEmpty() || filteredGeneration != PokeGenerationUiModel.ALL

    val filterCount
        get() =
            run {
                var count = 0
                if (filteredTypes.isNotEmpty()) count += filteredTypes.size
                if (filteredGeneration != PokeGenerationUiModel.ALL) count++
                count
            }
}
