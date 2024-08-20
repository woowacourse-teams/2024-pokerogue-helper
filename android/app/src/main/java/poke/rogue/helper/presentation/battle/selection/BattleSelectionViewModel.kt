package poke.rogue.helper.presentation.battle.selection

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleSelectionUiState
import poke.rogue.helper.presentation.battle.isSelected
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.isSkillSelectionRequired
import poke.rogue.helper.presentation.battle.selectedData

class BattleSelectionViewModel(
    private val previousSelection: SelectionData,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), NavigationHandler {
    private val _selectedPokemon =
        MutableStateFlow<BattleSelectionUiState<PokemonSelectionUiModel>>(BattleSelectionUiState.Empty)
    val selectedPokemon = _selectedPokemon.asStateFlow()

    private val _selectedSkill =
        MutableStateFlow<BattleSelectionUiState<SkillSelectionUiModel>>(BattleSelectionUiState.Empty)
    val selectedSkill = _selectedSkill.asStateFlow()

    private val _currentStep = MutableStateFlow(SelectionStep.POKEMON_SELECTION)
    val currentStep: StateFlow<SelectionStep> = _currentStep.asStateFlow()

    val isLastStep: StateFlow<Boolean> =
        currentStep.map {
            it.isLastStep(previousSelection.isSkillSelectionRequired())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val canGoNextStep: StateFlow<Boolean> =
        combine(
            currentStep,
            selectedPokemon,
            selectedSkill,
        ) { step, pokemonState, skillState ->
            when (step) {
                SelectionStep.POKEMON_SELECTION -> pokemonState.isSelected()
                SelectionStep.SKILL_SELECTION -> skillState.isSelected()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _completeSelection = MutableSharedFlow<SelectionData>()
    val completeSelection = _completeSelection.asSharedFlow()

    fun selectPokemon(pokemon: PokemonSelectionUiModel) {
        _selectedPokemon.value = BattleSelectionUiState.Selected(pokemon)
    }

    fun selectSkill(skill: SkillSelectionUiModel) {
        _selectedSkill.value = BattleSelectionUiState.Selected(skill)
    }

    override fun navigateToNextPage() {
        if (isLastStep.value) {
            handleSelectionResult()
            return
        }
        val nextIndex = currentStep.value.ordinal + 1
        val nextPage = SelectionStep.entries.getOrNull(nextIndex)
        if (nextPage != null) {
            _currentStep.value = nextPage
        }
    }

    private fun handleSelectionResult() {
        val pokemon =
            selectedPokemon.value.selectedData() ?: throw IllegalStateException("포켓몬을 선택하세요")
        val result =
            if (previousSelection.isSkillSelectionRequired()) {
                val skill =
                    selectedSkill.value.selectedData() ?: throw IllegalStateException("스킬을 선택하세요")
                SelectionData.WithSkill(pokemon, skill)
            } else {
                SelectionData.WithoutSkill(pokemon)
            }

        viewModelScope.launch {
            _completeSelection.emit(result)
        }
    }

    override fun navigateToPrevPage() {
        val pageIndex = currentStep.value.ordinal
        if (pageIndex == 0) return
        val prevPage = SelectionStep.entries.getOrNull(pageIndex - 1)
        if (prevPage != null) {
            _currentStep.value = prevPage
        }
    }

    companion object {
        fun factory(previousSelection: SelectionData): ViewModelProvider.Factory =
            BaseViewModelFactory { BattleSelectionViewModel(previousSelection) }
    }
}
