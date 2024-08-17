package poke.rogue.helper.presentation.battle.selection

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

class BattleSelectionViewModel(
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

    override fun selectPokemon(pokemon: PokemonSelectionUiModel) {
        _selectedPokemon.value = BattleSelectionUiState.Selected(pokemon)
    }

    override fun selectSkill(skill: SkillSelectionUiModel) {
        _selectedSkill.value = BattleSelectionUiState.Selected(skill)
    }

    override fun navigateToNextPage() {
        val nextPage =
            when (currentStep.value) {
                SelectionStep.POKEMON_SELECTION -> SelectionStep.SKILL_SELECTION
                SelectionStep.SKILL_SELECTION -> return // TODO : Complete Selection
            }
        _currentStep.value = nextPage
    }

    override fun navigateToPrevPage() {
        val prevPage =
            when (currentStep.value) {
                SelectionStep.POKEMON_SELECTION -> return
                SelectionStep.SKILL_SELECTION -> SelectionStep.POKEMON_SELECTION
            }
        _currentStep.value = prevPage
    }
}
