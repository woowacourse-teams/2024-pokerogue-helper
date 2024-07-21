package poke.rogue.helper.presentation.type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.repository.DefaultTypeRepository
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.type.mapper.toUiModel
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel.Companion.toUiModel

class TypeViewModel(
    private val typeRepository: TypeRepository,
) : ViewModel(), TypeHandler {
    private val _myType = MutableStateFlow<TypeSelectionUiState>(TypeSelectionUiState.Empty)
    val myType: StateFlow<TypeSelectionUiState> = _myType

    private val _opponentType1 = MutableStateFlow<TypeSelectionUiState>(TypeSelectionUiState.Empty)
    val opponentType1: StateFlow<TypeSelectionUiState> = _opponentType1

    private val _opponentType2 = MutableStateFlow<TypeSelectionUiState>(TypeSelectionUiState.Empty)
    val opponentType2: StateFlow<TypeSelectionUiState> = _opponentType2

    private val _typeEvent = MutableSharedFlow<TypeEvent>()
    val typeEvent: SharedFlow<TypeEvent> = _typeEvent.asSharedFlow()

    val allTypes: List<TypeUiModel> by lazy {
        typeRepository.allTypes().map { it.toUiModel() }
    }

    private val isAnyOpponentSelected
        get() = opponentType1.value is TypeSelectionUiState.Selected || opponentType2.value is TypeSelectionUiState.Selected

    val type: StateFlow<List<MatchedTypesUiModel>> =
        combine(
            myType,
            opponentType1,
            opponentType2,
        ) { mine, opponent1, opponent2 ->
            when {
                mine.isEmpty() && isAnyOpponentSelected ->
                    matchedTypesAgainstOpponentSelections(
                        opponent1,
                        opponent2,
                    )

                mine is TypeSelectionUiState.Selected && opponent1.isEmpty() && opponent2.isEmpty() ->
                    matchedTypesAgainstMySelection(mine)

                mine is TypeSelectionUiState.Selected && isAnyOpponentSelected ->
                    matchedTypes(mine, opponent1, opponent2)

                else -> emptyList()
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private fun matchedTypesAgainstOpponentSelections(
        opponent1: TypeSelectionUiState,
        opponent2: TypeSelectionUiState,
    ): List<MatchedTypesUiModel> {
        return matchedTypesAgainstOpponentSelection(opponent1) + matchedTypesAgainstOpponentSelection(opponent2)
    }

    private fun matchedTypesAgainstOpponentSelection(opponent: TypeSelectionUiState): List<MatchedTypesUiModel> {
        if (opponent is TypeSelectionUiState.Selected) {
            val selectedTypeId = opponent.selectedType.id

            return typeRepository.matchedTypesAgainstOpponent(selectedTypeId)
                .map { it.toUiModel(selectedTypeId, isMyType = false) }
        }
        return emptyList()
    }

    private fun matchedTypesAgainstMySelection(mine: TypeSelectionUiState.Selected): List<MatchedTypesUiModel> {
        val selectedTypeId = mine.selectedType.id

        return typeRepository.matchedTypesAgainstMyType(selectedTypeId)
            .map { it.toUiModel(selectedTypeId, isMyType = true) }
    }

    private fun matchedTypes(
        mine: TypeSelectionUiState.Selected,
        opponent1: TypeSelectionUiState,
        opponent2: TypeSelectionUiState,
    ): List<MatchedTypesUiModel> {
        val mySelectedId = mine.selectedType.id

        val opponentIds =
            listOf(opponent1, opponent2)
                .filterIsInstance<TypeSelectionUiState.Selected>()
                .map { it.selectedType.id }

        return typeRepository.matchedTypes(mySelectedId, opponentIds)
            .map { it.toUiModel(mySelectedId, isMyType = true) }
    }

    private fun TypeSelectionUiState.isEmpty(): Boolean = this is TypeSelectionUiState.Empty

    override fun startSelection(selectorType: SelectorType) {
        viewModelScope.launch {
            _typeEvent.emit(TypeEvent.ShowSelection(selectorType))
        }
    }

    override fun selectType(
        selectorType: SelectorType,
        selectedType: TypeUiModel,
    ) {
        val changedState = TypeSelectionUiState.Selected(selectedType)
        updateSelectionState(selectorType, changedState)
        viewModelScope.launch {
            _typeEvent.emit(TypeEvent.HideSelection)
        }
    }

    override fun removeSelection(selectorType: SelectorType) {
        val changedState = TypeSelectionUiState.Empty
        updateSelectionState(selectorType, changedState)
    }

    private fun updateSelectionState(
        selectorType: SelectorType,
        changedState: TypeSelectionUiState,
    ) {
        viewModelScope.launch {
            when (selectorType) {
                SelectorType.MINE -> _myType.value = changedState
                SelectorType.OPPONENT1 -> _opponentType1.value = changedState
                SelectorType.OPPONENT2 -> _opponentType2.value = changedState
            }
        }
    }

    override fun removeAllSelections() {
        viewModelScope.launch {
            _myType.value = TypeSelectionUiState.Empty
            _opponentType1.value = TypeSelectionUiState.Empty
            _opponentType2.value = TypeSelectionUiState.Empty
        }
    }

    companion object {
        fun factory() = BaseViewModelFactory { TypeViewModel(DefaultTypeRepository(LocalTypeDataSource())) }
    }
}
