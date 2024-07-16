package poke.rogue.helper.presentation.type

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.repository.TypeRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.type.mapper.toUiModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel
import poke.rogue.helper.presentation.type.model.TypeSelectionUiState
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.Event

class TypeViewModel(
    private val typeRepository: TypeRepository,
) : ViewModel(), TypeHandler {
    private val _myType = MutableLiveData<TypeSelectionUiState>(TypeSelectionUiState.Idle)
    val myType: LiveData<TypeSelectionUiState> = _myType

    private val _opponentType1 = MutableLiveData<TypeSelectionUiState>(TypeSelectionUiState.Idle)
    val opponentType1: LiveData<TypeSelectionUiState> = _opponentType1

    private val _opponentType2 = MutableLiveData<TypeSelectionUiState>(TypeSelectionUiState.Idle)
    val opponentType2: LiveData<TypeSelectionUiState> = _opponentType2

    private val _navigateEvent = MutableLiveData<Event<Boolean>>()
    val navigateEvent: LiveData<Event<Boolean>> = _navigateEvent

    private val _type: MutableLiveData<List<TypeMatchedResultUiModel>> = MutableLiveData()
    val type: LiveData<List<TypeMatchedResultUiModel>> = _type

    init {
        viewModelScope.launch {
            combine(
                myType.asFlow(),
                opponentType1.asFlow(),
                opponentType2.asFlow(),
            ) { my, op1, op2 ->
                when {
                    // 1) my is empty, op1 and op2 are not empty
                    my.isEmpty() && (op1.isEmpty().not() || op2.isEmpty().not()) -> {
                        val results = mutableListOf<TypeMatchedResultUiModel>()
                        if (op1 is TypeSelectionUiState.Selected) {
                            val tmp1 = op1.selectedType.id
                            results.addAll(
                                typeRepository.findResultAgainstOpponent(tmp1)
                                    .map { it.toUiModel(tmp1, false) },
                            )
                        }
                        if (op2 is TypeSelectionUiState.Selected) {
                            val tmp2 = op2.selectedType.id
                            results.addAll(
                                typeRepository.findResultAgainstOpponent(tmp2)
                                    .map { it.toUiModel(tmp2, false) },
                            )
                        }
                        results
                    }
                    // 2) my is not empty, op1 and op2 are empty
                    !my.isEmpty() && op1.isEmpty() && op2.isEmpty() -> {
                        val myId = (my as TypeSelectionUiState.Selected).selectedType.id
                        typeRepository.findResultAgainstMyType(myId)
                            .map { it.toUiModel(myId, true) }
                    }
                    // 3) my is not empty, op1 and/or op2 are not empty
                    !my.isEmpty() && (op1.isEmpty().not() || op2.isEmpty().not()) -> {
                        val myId = (my as TypeSelectionUiState.Selected).selectedType.id
                        val results = mutableListOf<TypeMatchedResultUiModel>()
                        val tmpIds = mutableListOf<Int>()
                        if (op1 is TypeSelectionUiState.Selected) {
                            val tmp1 = op1.selectedType.id
                            tmpIds.add(tmp1)
                        }
                        if (op2 is TypeSelectionUiState.Selected) {
                            val tmp2 = op2.selectedType.id
                            tmpIds.add(tmp2)
                        }
                        results.addAll(
                            typeRepository.findMatchedResult(myId, tmpIds)
                                .map { it.toUiModel(myId, true) },
                        )
                        results
                    }

                    else -> {
                        emptyList()
                    }
                }
            }.collect { result ->
                _type.value = result
            }
        }
    }

    private fun TypeSelectionUiState.isEmpty(): Boolean = this is TypeSelectionUiState.Idle

    override fun selectType(
        selectorType: SelectorType,
        selectedType: TypeUiModel,
    ) {
        val changedState = TypeSelectionUiState.Selected(selectedType)
        when (selectorType) {
            SelectorType.MINE -> _myType.value = changedState
            SelectorType.OPPONENT1 -> _opponentType1.value = changedState
            SelectorType.OPPONENT2 -> _opponentType2.value = changedState
        }
    }

    override fun deleteMyType() {
        _myType.value = TypeSelectionUiState.Idle
    }

    override fun deleteOpponent1Type() {
        _opponentType1.value = TypeSelectionUiState.Idle
    }

    override fun deleteOpponent2Type() {
        _opponentType2.value = TypeSelectionUiState.Idle
    }

    companion object {
        fun factory() = BaseViewModelFactory { TypeViewModel(TypeRepository(LocalTypeDataSource())) }
    }
}
