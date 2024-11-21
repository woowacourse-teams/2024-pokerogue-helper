package poke.rogue.helper.presentation.dex.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.util.event.EventFlow
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow

class PokeFilterViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val uiState: StateFlow<PokeFilterUiState> =
        savedStateHandle.getStateFlow(
            UI_STATE_KEY,
            PokeFilterUiState.DEFAULT,
        )

    private val _uiEvent = MutableEventFlow<PokeFilterUiEvent>()
    val uiEvent: EventFlow<PokeFilterUiEvent> = _uiEvent.asEventFlow()

    fun init(args: PokeFilterUiModel) {
        savedStateHandle[UI_STATE_KEY] =
            PokeFilterUiState(
                types =
                    TypeUiModel1.entries.mapIndexed { index, typeUiModel1 ->
                        SelectableUiModel(
                            index,
                            args.selectedTypes.contains(typeUiModel1),
                            typeUiModel1,
                        )
                    },
                generations =
                    PokeGenerationUiModel.entries.mapIndexed { index, pokeGenerationUiModel ->
                        SelectableUiModel(
                            index,
                            args.selectedGeneration == pokeGenerationUiModel,
                            pokeGenerationUiModel,
                        )
                    },
                selectedTypes = args.selectedTypes,
            )
    }

    fun selectType(id: Int) {
        val selectedTypes = uiState.value.selectedTypes
        val types = uiState.value.types
        if (selectedTypes.size < LIMIT_TYPE_COUNT) {
            return selectTypeWithinLimit(id, types, selectedTypes)
        }
        if (selectedTypes.any { it.id == id }) {
            selectTypeWithinLimit(id, types, selectedTypes)
            return
        }
        selectTypeExceedingLimit(id, types, selectedTypes)
    }

    private fun selectTypeWithinLimit(
        id: Int,
        types: List<SelectableUiModel<TypeUiModel1>>,
        selectedTypes: List<TypeUiModel1>,
    ) {
        var newSelectedTypes = selectedTypes
        val newTypes =
            types.map { type ->
                if (type.id == id) {
                    newSelectedTypes =
                        if (type.isSelected) {
                            selectedTypes - type.data
                        } else {
                            selectedTypes + type.data
                        }
                    return@map type.copy(isSelected = !type.isSelected)
                }
                type
            }
        savedStateHandle[UI_STATE_KEY] =
            uiState.value.copy(types = newTypes, selectedTypes = newSelectedTypes)
    }

    private fun selectTypeExceedingLimit(
        id: Int,
        types: List<SelectableUiModel<TypeUiModel1>>,
        selectedTypes: List<TypeUiModel1>,
    ) {
        var newSelectedTypes = selectedTypes
        val firstSelectedType = selectedTypes.first()
        val newTypes =
            types.map { type ->
                if (type.data == firstSelectedType) {
                    return@map type.copy(isSelected = false)
                }
                if (type.id == id) {
                    newSelectedTypes = selectedTypes.drop(1) + type.data
                    return@map type.copy(isSelected = !type.isSelected)
                }
                type
            }
        savedStateHandle[UI_STATE_KEY] =
            uiState.value.copy(types = newTypes, selectedTypes = newSelectedTypes)
    }

    fun toggleGeneration(generationId: Int) {
        val generations = uiState.value.generations
        if (generations[generationId].isSelected) return
        val newGenerations =
            uiState.value.generations.map { type ->
                if (type.id == generationId) {
                    type.copy(isSelected = !type.isSelected)
                } else {
                    type.copy(isSelected = false)
                }
            }
        savedStateHandle[UI_STATE_KEY] = uiState.value.copy(generations = newGenerations)
    }

    fun applyFiltering() {
        viewModelScope.launch {
            _uiEvent.emit(
                PokeFilterUiEvent.ApplyFiltering(
                    selectedTypes = uiState.value.selectedTypes,
                    generation = uiState.value.selectedGeneration,
                ),
            )
        }
    }

    fun closeFilter() {
        viewModelScope.launch {
            _uiEvent.emit(PokeFilterUiEvent.CloseFilter)
        }
    }

    companion object {
        private const val UI_STATE_KEY = "uiState"
        private const val LIMIT_TYPE_COUNT: Int = 2
    }
}
