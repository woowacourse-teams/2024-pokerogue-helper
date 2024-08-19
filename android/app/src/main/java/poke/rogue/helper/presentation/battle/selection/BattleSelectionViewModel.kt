package poke.rogue.helper.presentation.battle.selection

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleSelectionUiState
import poke.rogue.helper.presentation.battle.isSelected
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

class BattleSelectionViewModel(
    private val isSkillSelectionRequired: Boolean,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), BattleSelectionHandler, NavigationHandler {
    private val _pokemons = MutableStateFlow(PokemonSelectionUiModel.DUMMY)
    val pokemons = _pokemons.asStateFlow()

    private val _selectedPokemon =
        MutableStateFlow<BattleSelectionUiState<PokemonSelectionUiModel>>(BattleSelectionUiState.Empty)
    val selectedPokemon = _selectedPokemon.asStateFlow()

    private val _skills = MutableStateFlow(SkillSelectionUiModel.DUMMY)
    val skills = _skills.asStateFlow()

    private val _selectedSkill =
        MutableStateFlow<BattleSelectionUiState<SkillSelectionUiModel>>(BattleSelectionUiState.Empty)
    val selectedSkill = _selectedSkill.asStateFlow()

    private val _currentStep = MutableStateFlow(SelectionStep.POKEMON_SELECTION)
    val currentStep: StateFlow<SelectionStep> = _currentStep.asStateFlow()

    val isLastStep: StateFlow<Boolean> =
        currentStep.map {
            it.isLastStep(isSkillSelectionRequired)
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

    override fun selectPokemon(pokemon: PokemonSelectionUiModel) {
        _selectedPokemon.value = BattleSelectionUiState.Selected(pokemon)
    }

    override fun selectSkill(skill: SkillSelectionUiModel) {
        _selectedSkill.value = BattleSelectionUiState.Selected(skill)
    }

    override fun navigateToNextPage() {
        if (isLastStep.value) {
            // TODO : Complete Selection
            return
        }
        val nextIndex = currentStep.value.ordinal + 1
        val nextPage = SelectionStep.entries.getOrNull(nextIndex)
        if (nextPage != null) {
            _currentStep.value = nextPage
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
        fun factory(hasSkillSelection: Boolean): ViewModelProvider.Factory =
            BaseViewModelFactory { BattleSelectionViewModel(hasSkillSelection) }
    }
}
