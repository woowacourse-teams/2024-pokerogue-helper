package poke.rogue.helper.presentation.dex.sort

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.util.event.EventFlow
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow

class PokeSortViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), PokemonSortHandler {
    val uiState: StateFlow<PokeSortUiState> =
        savedStateHandle.getStateFlow(
            key = UI_STATE_KEY,
            initialValue = PokeSortUiState.Default,
        )

    private val _uiEvent = MutableEventFlow<PokeSortUiEvent>()
    val uiEvent: EventFlow<PokeSortUiEvent> = _uiEvent.asEventFlow()

    fun init(sort: PokemonSortUiModel) {
        savedStateHandle[UI_STATE_KEY] = PokeSortUiState(sort)
    }

    override fun toggleSort(id: Int) {
        uiState.value.pokemonSorts.forEach { sort ->
            if (sort.id == id) {
                val newSort =
                    uiState.value.pokemonSorts.map { type ->
                        if (type.id == id) {
                            type.copy(isSelected = !type.isSelected)
                        } else {
                            type.copy(isSelected = false)
                        }
                    }
                savedStateHandle[UI_STATE_KEY] = uiState.value.copy(pokemonSorts = newSort)
                applySorting()
            }
        }
    }

    private fun applySorting() {
        viewModelScope.launch {
            _uiEvent.emit(
                PokeSortUiEvent.ApplySorting(
                    uiState.value.selectedSort,
                ),
            )
        }
    }

    fun closeSort() {
        viewModelScope.launch {
            _uiEvent.emit(PokeSortUiEvent.CloseSort)
        }
    }

    companion object {
        private const val UI_STATE_KEY = "uiState"
    }
}

sealed interface PokeSortUiEvent {
    data object CloseSort : PokeSortUiEvent

    data class ApplySorting(val sort: PokemonSortUiModel) : PokeSortUiEvent
}

@Parcelize
data class PokeSortUiState(
    val pokemonSorts: List<SelectableUiModel<PokemonSortUiModel>>,
) : Parcelable {
    constructor(pokemonSort: PokemonSortUiModel) : this(
        pokemonSorts = pokemonSortsFrom(pokemonSort),
    )

    val selectedSort: PokemonSortUiModel
        get() = pokemonSorts.first { it.isSelected }.data

    companion object {
        val Default =
            PokeSortUiState(
                pokemonSorts = pokemonSortsFrom(PokemonSortUiModel.ByDexNumber),
            )

        private fun pokemonSortsFrom(sort: PokemonSortUiModel) =
            PokemonSortUiModel.entries.map { type ->
                if (type == sort) {
                    SelectableUiModel(
                        id = type.id,
                        isSelected = true,
                        data = type,
                    )
                } else {
                    SelectableUiModel(
                        id = type.id,
                        isSelected = false,
                        data = type,
                    )
                }
            }
    }
}
