package poke.rogue.helper.presentation.battle.selection.pokemon

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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.toSelectionUi
import poke.rogue.helper.presentation.battle.selection.QueryHandler
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.dex.filter.toSelectableModelsBy
import poke.rogue.helper.stringmatcher.has

class PokemonSelectionViewModel(
    private val dexRepository: DexRepository,
    previousSelection: PokemonSelectionUiModel?,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), PokemonSelectionHandler, QueryHandler {
    private val _pokemonSelectedEvent = MutableSharedFlow<PokemonSelectionUiModel>()
    val pokemonSelectedEvent = _pokemonSelectedEvent.asSharedFlow()

    private val _pokemons =
        MutableStateFlow<List<SelectableUiModel<PokemonSelectionUiModel>>>(emptyList())
    val pokemons = _pokemons.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredPokemon: StateFlow<List<SelectableUiModel<PokemonSelectionUiModel>>> =
        searchQuery
            .debounce(300L)
            .flatMapLatest { query ->
                pokemons.map { pokemonsList ->
                    if (query.isBlank()) {
                        pokemonsList
                    } else {
                        pokemonsList.filter { it.data.name.has(query) }
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), pokemons.value)

    init {
        viewModelScope.launch(errorHandler) {
            val pokemonList =
                dexRepository.pokemons()
                    .map { it.toSelectionUi() }
                    .toSelectableModelsBy { previousSelection?.id == it.id }
            _pokemons.value = pokemonList
        }
    }

    override fun selectPokemon(selected: PokemonSelectionUiModel) {
        _pokemons.value =
            pokemons.value.map {
                val isSelected = it.data.id == selected.id
                it.copy(isSelected = isSelected)
            }
        viewModelScope.launch {
            _pokemonSelectedEvent.emit(selected)
        }
    }

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }
}
