package poke.rogue.helper.presentation.battle

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.BattleResultUiModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.WeatherUiModel

class BattleViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger),
    BattleNavigationHandler {
    private val _weathers = MutableStateFlow(WeatherUiModel.DUMMY)
    val weathers = _weathers.asStateFlow()

    private val _selectedState = MutableStateFlow(BattleSelectionsState.DEFAULT)
    val selectedState = _selectedState.asStateFlow()

    private val _navigateToSelection = MutableSharedFlow<SelectionData>()
    val navigateToSelection = _navigateToSelection.asSharedFlow()

    val battleResult: StateFlow<BattleResultUiState> =
        selectedState.map {
            if (it.allSelected) {
                BattleResultUiState.Success(BattleResultUiModel.DUMMY)
            } else {
                BattleResultUiState.Idle
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BattleResultUiState.Idle)

    val isBattleFetchSuccessful =
        battleResult.map { it.isSuccess() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun updateWeather(newWeather: WeatherUiModel) {
        viewModelScope.launch {
            _selectedState.value = selectedState.value.copy(weather = newWeather)
        }
    }

    fun updatePokemonSelection(selection: SelectionData) {
        when (selection) {
            is SelectionData.WithSkill ->
                updateMyPokemon(
                    selection.selectedPokemon,
                    selection.selectedSkill,
                )

            is SelectionData.WithoutSkill -> updateOpponentPokemon(selection.selectedPokemon)
            is SelectionData.NoSelection -> {}
        }
    }

    private fun updateMyPokemon(
        pokemon: PokemonSelectionUiModel,
        skill: SkillSelectionUiModel,
    ) {
        viewModelScope.launch {
            val selectedPokemon = BattleSelectionUiState.Selected(pokemon)
            val selectedSkill = BattleSelectionUiState.Selected(skill)
            _selectedState.value =
                selectedState.value.copy(minePokemon = selectedPokemon, skill = selectedSkill)
        }
    }

    private fun updateOpponentPokemon(pokemon: PokemonSelectionUiModel) {
        viewModelScope.launch {
            val selectedPokemon = BattleSelectionUiState.Selected(pokemon)
            _selectedState.value = selectedState.value.copy(opponentPokemon = selectedPokemon)
        }
    }

    override fun navigateToSelection(hasSkillSelection: Boolean) {
        viewModelScope.launch {
            val previousPokemonSelection =
                if (hasSkillSelection) {
                    selectedState.value.minePokemon.selectedData()
                } else {
                    selectedState.value.opponentPokemon.selectedData()
                }
            val previousSelection =
                if (previousPokemonSelection != null) {
                    previousSelection(hasSkillSelection, previousPokemonSelection)
                } else {
                    SelectionData.NoSelection(hasSkillSelection)
                }
            _navigateToSelection.emit(previousSelection)
        }
    }

    private fun previousSelection(
        hasSkillSelection: Boolean,
        previousPokemonSelection: PokemonSelectionUiModel,
    ): SelectionData {
        return if (hasSkillSelection) {
            val skill =
                selectedState.value.skill.selectedData()
                    ?: throw IllegalStateException("")
            SelectionData.WithSkill(previousPokemonSelection, skill)
        } else {
            SelectionData.WithoutSkill(previousPokemonSelection)
        }
    }
}