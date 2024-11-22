package poke.rogue.helper.presentation.battle.selection

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
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleSelectionUiState
import poke.rogue.helper.presentation.battle.isSelected
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SelectionMode
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.isSkillSelectionRequired
import poke.rogue.helper.presentation.battle.model.selectedPokemon
import poke.rogue.helper.presentation.battle.model.selectedSkill
import poke.rogue.helper.presentation.battle.selectedData

class BattleSelectionViewModel(
    private val selectionMode: SelectionMode,
    val previousSelection: SelectionData,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), NavigationHandler {
    private val _selectedPokemon: MutableStateFlow<BattleSelectionUiState<PokemonSelectionUiModel>> =
        MutableStateFlow(initializeSelectedPokemon())
    val selectedPokemon = _selectedPokemon.asStateFlow()

    private val _pokemonSelectionUpdate = MutableSharedFlow<Long>(replay = 1)
    val pokemonSelectionUpdate = _pokemonSelectionUpdate.asSharedFlow()

    private val _selectedSkill: MutableStateFlow<BattleSelectionUiState<SkillSelectionUiModel>> =
        MutableStateFlow(initializeSelectedSkill())
    val selectedSkill = _selectedSkill.asStateFlow()

    private val _currentStep = MutableStateFlow(initialStep(selectionMode))
    val currentStep: StateFlow<SelectionStep> = _currentStep.asStateFlow()

    val isLastStep: StateFlow<Boolean> =
        currentStep.map {
            it.isLastStep(selectionMode.isSkillSelectionRequired())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _completeSelection = MutableSharedFlow<SelectionData>()
    val completeSelection = _completeSelection.asSharedFlow()

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

    private fun initializeSelectedPokemon(): BattleSelectionUiState<PokemonSelectionUiModel> {
        val selectedPokemon = previousSelection.selectedPokemon()
        return if (selectedPokemon != null) {
            BattleSelectionUiState.Selected(selectedPokemon)
        } else {
            BattleSelectionUiState.Empty
        }
    }

    private fun initializeSelectedSkill(): BattleSelectionUiState<SkillSelectionUiModel> {
        val selectedSkill = previousSelection.selectedSkill()
        return if (selectedSkill != null) {
            BattleSelectionUiState.Selected(selectedSkill)
        } else {
            BattleSelectionUiState.Empty
        }
    }

    private fun initialStep(selectionMode: SelectionMode): SelectionStep =
        when {
            selectionMode == SelectionMode.SKILL_FIRST && previousSelection != SelectionData.NoSelection -> {
                val selected =
                    requireNotNull(previousSelection.selectedPokemon()) { "포켓몬이 선택되지 않았습니다." }
                updateDexNumberForSkills(selected.dexNumber)
                SelectionStep.SKILL_SELECTION
            }

            else -> SelectionStep.POKEMON_SELECTION
        }

    fun selectPokemon(pokemon: PokemonSelectionUiModel) {
        _selectedPokemon.value = BattleSelectionUiState.Selected(pokemon)
        _selectedSkill.value = BattleSelectionUiState.Empty
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
        val nextPage = SelectionStep.entries.getOrNull(nextIndex) ?: error("잘못된 페이지 접근")
        if (nextPage == SelectionStep.SKILL_SELECTION) {
            val selected =
                requireNotNull(selectedPokemon.value.selectedData()) { "포켓몬이 선택되어야 합니다." }
            updateDexNumberForSkills(selected.dexNumber)
        }
        _currentStep.value = nextPage
    }

    private fun updateDexNumberForSkills(dexNumber: Long) {
        viewModelScope.launch {
            _pokemonSelectionUpdate.emit(dexNumber)
        }
    }

    private fun handleSelectionResult() {
        val pokemon = requireNotNull(selectedPokemon.value.selectedData()) { "포켓몬이 선택되어야 합니다." }
        val result =
            if (selectionMode.isSkillSelectionRequired()) {
                val skill = requireNotNull(selectedSkill.value.selectedData()) { "스킬이 선택되어야 합니다." }
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
}
