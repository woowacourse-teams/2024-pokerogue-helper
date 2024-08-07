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
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModelComparator
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
                    matchedTypesAgainstOpponents(states.selectedOpponents)

                states.isMyTypeSelectedAndOpponentsEmpty ->
                    matchedTypesAgainstMine(states.myType as TypeSelectionUiState.Selected)

                states.isMyTypeSelectedAndAnyOpponentSelected ->
                    matchedTypes(
                        states.myType as TypeSelectionUiState.Selected,
                        states.selectedOpponents,
                    )

                else -> emptyList()
            }.sortedWith(MatchedTypesUiModelComparator)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    private fun matchedTypesAgainstOpponents(opponents: List<TypeSelectionUiState.Selected>): List<MatchedTypesUiModel> {
        return opponents.flatMap { matchedTypesAgainstOpponent(it) }
    }

    private fun matchedTypesAgainstOpponent(opponent: TypeSelectionUiState.Selected): List<MatchedTypesUiModel> {
        val selectedTypeId = opponent.selectedType.id
        return typeRepository.matchedTypesAgainstOpponent(selectedTypeId)
            .toUi(selectedTypeId = selectedTypeId, isMyType = false)
    }

    private fun matchedTypesAgainstMine(mine: TypeSelectionUiState.Selected): List<MatchedTypesUiModel> {
        val selectedTypeId = mine.selectedType.id
        return typeRepository.matchedTypesAgainstMyType(selectedTypeId)
            .toUi(selectedTypeId = selectedTypeId, isMyType = true)
    }

    private fun matchedTypes(
        mine: TypeSelectionUiState.Selected,
        opponents: List<TypeSelectionUiState.Selected>,
    ): List<MatchedTypesUiModel> {
        val mySelectedId = mine.selectedType.id
        val opponentIds = opponents.map { it.selectedType.id }

        return typeRepository.matchedTypes(mySelectedId, opponentIds)
            .toUi(selectedTypeId = mySelectedId, isMyType = true)
    }

    override fun startSelection(selectorType: SelectorType) {
        viewModelScope.launch {
            _typeEvent.emit(
                TypeEvent.ShowSelection(
                    selectorType,
                    typeSelectionStates.value.disabledTypeItems(selectorType),
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
        fun factory() =
            BaseViewModelFactory { TypeViewModel(DefaultTypeRepository(LocalTypeDataSource())) }
    }
}