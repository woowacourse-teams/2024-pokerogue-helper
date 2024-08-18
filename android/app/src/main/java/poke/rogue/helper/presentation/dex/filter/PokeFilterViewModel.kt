package poke.rogue.helper.presentation.dex.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.event.EventFlow
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow
import timber.log.Timber

class PokeFilterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PokeFilterUiState.DEFAULT)
    val uiState: StateFlow<PokeFilterUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableEventFlow<PokeFilterUiEvent>()
    val uiEvent: EventFlow<PokeFilterUiEvent> = _uiEvent.asEventFlow()

    fun init(args: PokeFilterUiModel) {
        _uiState.value = PokeFilterUiState(
            types = TypeUiModel.entries.mapIndexed { index, typeUiModel ->
                SelectableUiModel(
                    index,
                    args.selectedTypes.contains(typeUiModel),
                    typeUiModel,
                )
            },
            generations = PokeGenerationUiModel.entries.mapIndexed { index, pokeGenerationUiModel ->
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
        if (selectedTypes.size < Limit_TYPE_COUNT) {
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
        types: List<SelectableUiModel<TypeUiModel>>,
        selectedTypes: List<TypeUiModel>
    ) {
        var newSelectedTypes = selectedTypes
        val newTypes = types.map { type ->
            if (type.id == id) {
                newSelectedTypes = if (type.isSelected) {
                    selectedTypes - type.data
                } else {
                    selectedTypes + type.data
                }
                return@map type.copy(isSelected = !type.isSelected)
            }
            type
        }
        _uiState.value = uiState.value.copy(types = newTypes, selectedTypes = newSelectedTypes)
    }

    private fun selectTypeExceedingLimit(
        id: Int,
        types: List<SelectableUiModel<TypeUiModel>>,
        selectedTypes: List<TypeUiModel>
    ) {
        var newSelectedTypes = selectedTypes
        val firstSelectedType = selectedTypes.first()
        val newTypes = types.map { type ->
            if (type.data == firstSelectedType) {
                return@map type.copy(isSelected = false)
            }
            if (type.id == id) {
                newSelectedTypes = selectedTypes.drop(1) + type.data
                return@map type.copy(isSelected = !type.isSelected)
            }
            type
        }
        _uiState.value = uiState.value.copy(types = newTypes, selectedTypes = newSelectedTypes)
    }

    fun toggleGeneration(generationId: Int) {
        val generations = uiState.value.generations
        if (generations[generationId].isSelected) return
        val newGenerations = uiState.value.generations.map { type ->
            if (type.id == generationId) {
                type.copy(isSelected = !type.isSelected)
            } else {
                type.copy(isSelected = false)
            }
        }
        _uiState.value = uiState.value.copy(generations = newGenerations)
    }

    fun applyFiltering() {
        viewModelScope.launch {
            Timber.d("applyFiltering: ${uiState.value.selectedTypes}")
            _uiEvent.emit(
                PokeFilterUiEvent.ApplyFiltering(
                    selectedTypes = uiState.value.selectedTypes,
                    generation = uiState.value.selectedGeneration
                )
            )
        }
    }

    fun closeFilter() {
        viewModelScope.launch {
            _uiEvent.emit(PokeFilterUiEvent.CloseFilter)
        }
    }

    companion object {
        private const val Limit_TYPE_COUNT = 2
    }
}



