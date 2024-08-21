package poke.rogue.helper.presentation.battle.selection.pokemon

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.QueryHandler
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel

class PokemonSelectionViewModel(
    private val dexRepository: DexRepository,
    previousSelection: PokemonSelectionUiModel?,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), PokemonSelectionHandler, QueryHandler {
    private val _pokemonSelectedEvent = MutableSharedFlow<PokemonSelectionUiModel>()
    val pokemonSelectedEvent = _pokemonSelectedEvent.asSharedFlow()

    private val _pokemons: MutableStateFlow<List<SelectableUiModel<PokemonSelectionUiModel>>>
    val pokemons: StateFlow<List<SelectableUiModel<PokemonSelectionUiModel>>>

    private val searchQuery = MutableStateFlow("")

    init {
        _pokemons =
            MutableStateFlow(
                PokemonSelectionUiModel.DUMMY.mapIndexed { index, pokemon ->
                    SelectableUiModel(index, previousSelection?.id == pokemon.id, pokemon)
                },
            )
        pokemons = _pokemons.asStateFlow()
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

    companion object {
        fun factory(
            dexRepository: DexRepository,
            previousSelection: PokemonSelectionUiModel?,
        ): ViewModelProvider.Factory = BaseViewModelFactory { PokemonSelectionViewModel(dexRepository, previousSelection) }
    }
}
