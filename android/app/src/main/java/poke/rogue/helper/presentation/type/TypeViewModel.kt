package poke.rogue.helper.presentation.type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.repository.DefaultTypeRepository
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.type.mapper.sortedAndMappedToUi
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

class TypeViewModel(
    private val typeRepository: TypeRepository,
) : ViewModel(), TypeHandler {
    private val _typeSelectionStates = MutableStateFlow(TypeSelectionStates())
    val typeSelectionStates: StateFlow<TypeSelectionStates> = _typeSelectionStates

    private val _typeEvent = MutableSharedFlow<TypeEvent>()
    val typeEvent: SharedFlow<TypeEvent> = _typeEvent.asSharedFlow()

    val allTypes: List<TypeUiModel> by lazy {
        typeRepository.allTypes().map { it.toUi() }
    }

    val type: StateFlow<List<MatchedTypesUiModel>> =
        typeSelectionStates.map { states ->
            when {
                states.isMyTypeEmptyAndAnyOpponentSelected ->
                    matchedTypesAgainstOpponentSelections(
                        states.opponentType1,
                        states.opponentType2,
                    )

                states.isMyTypeSelectedAndOpponentsEmpty ->
                    matchedTypesAgainstMySelection(states.myType as TypeSelectionUiState.Selected)

                states.isMyTypeSelectedAndAnyOpponentSelected ->
                    matchedTypes(
                        states.myType as TypeSelectionUiState.Selected,
                        states.opponentType1,
                        states.opponentType2,
                    )

                else -> emptyList()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

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
                .sortedAndMappedToUi(selectedTypeId = selectedTypeId, isMyType = false)
        }
        return emptyList()
    }

    private fun matchedTypesAgainstMySelection(mine: TypeSelectionUiState.Selected): List<MatchedTypesUiModel> {
        val selectedTypeId = mine.selectedType.id
        return typeRepository.matchedTypesAgainstMyType(selectedTypeId)
            .sortedAndMappedToUi(selectedTypeId = selectedTypeId, isMyType = true)
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
            .sortedAndMappedToUi(selectedTypeId = mySelectedId, isMyType = true)
    }

    override fun startSelection(selectorType: SelectorType) {
        viewModelScope.launch {
            _typeEvent.emit(
                TypeEvent.ShowSelection(
                    selectorType,
                    typeSelectionStates.value.disabledTypeItemSet(selectorType),
                ),
            )
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
        _typeSelectionStates.value =
            when (selectorType) {
                SelectorType.MINE -> _typeSelectionStates.value.copy(myType = changedState)
                SelectorType.OPPONENT1 -> _typeSelectionStates.value.copy(opponentType1 = changedState)
                SelectorType.OPPONENT2 -> _typeSelectionStates.value.copy(opponentType2 = changedState)
            }
    }

    override fun removeAllSelections() {
        _typeSelectionStates.value =
            _typeSelectionStates.value.copy(
                myType = TypeSelectionUiState.Empty,
                opponentType1 = TypeSelectionUiState.Empty,
                opponentType2 = TypeSelectionUiState.Empty,
            )
    }

    companion object {
        fun factory() = BaseViewModelFactory { TypeViewModel(DefaultTypeRepository(LocalTypeDataSource())) }
    }
}
