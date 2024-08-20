package poke.rogue.helper.presentation.battle.selection.pokemon

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.SelectableUiModel

class PokemonSelectionViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger),
    PokemonSelectionHandler {
    private val _pokemons =
        MutableStateFlow(
            PokemonSelectionUiModel.DUMMY.mapIndexed { index, pokemon ->
                SelectableUiModel(index, false, pokemon)
            },
        )
    val pokemons = _pokemons.asStateFlow()

    private val _pokemonSelectedEvent = MutableSharedFlow<PokemonSelectionUiModel>()
    val pokemonSelectedEvent = _pokemonSelectedEvent.asSharedFlow()

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
}
