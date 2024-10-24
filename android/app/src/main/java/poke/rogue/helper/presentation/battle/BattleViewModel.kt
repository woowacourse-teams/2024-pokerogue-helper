package poke.rogue.helper.presentation.battle

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.BattleRepository
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.BattlePredictionUiModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SelectionMode
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.battle.model.toSelectionUi
import poke.rogue.helper.presentation.battle.model.toUi

class BattleViewModel(
    private val battleRepository: BattleRepository,
    private val pokemonRepository: DexRepository,
    private val logger: AnalyticsLogger = analyticsLogger(),
    pokemonId: String? = null,
    selectionType: SelectionType? = null,
) : ErrorHandleViewModel(logger), BattleNavigationHandler {
    private val _weathers = MutableStateFlow(emptyList<WeatherUiModel>())
    val weathers = _weathers.asStateFlow()

    private val _selectedState = MutableStateFlow(BattleSelectionsState.DEFAULT)
    val selectedState = _selectedState.asStateFlow()

    val weatherPos: StateFlow<Int> =
        combine(
            battleRepository.weatherStream(),
            weathers,
        ) { weather, weathers ->
            if (weathers.isEmpty()) return@combine null
            val selectedWeather =
                weather?.toUi()?.let { uiWeather -> weathers.find { it.id == uiWeather.id } }
                    ?: weathers.first()

            _selectedState.value =
                selectedState.value.copy(weather = BattleSelectionUiState.Selected(selectedWeather))

            weathers.indexOfFirst { it.id == selectedWeather.id }
        }.filterNotNull().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _navigationEvent = MutableSharedFlow<BattleNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    val battleResult: StateFlow<BattleResultUiState> =
        selectedState.map {
            if (it.allSelected) {
                val result = fetchBattlePredictionResult()
                BattleResultUiState.Success(result)
            } else {
                BattleResultUiState.Idle
            }
        }.stateIn(
            viewModelScope + errorHandler,
            SharingStarted.WhileSubscribed(5000),
            BattleResultUiState.Idle,
        )

    val isBattleFetchSuccessful =
        battleResult.map { it.isSuccess() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initWeathers()
        handlePokemonSelection(pokemonId, selectionType)
    }

    private suspend fun fetchBattlePredictionResult(): BattlePredictionUiModel {
        with(selectedState.value) {
            val weatherId = requireNotNull(weather.selectedData()?.id) { "날씨는 null일 수 없습니다." }
            val myPokemonId =
                requireNotNull(minePokemon.selectedData()?.id) { "내 포켓몬은 null일 수 없습니다." }
            val mySkillId = requireNotNull(skill.selectedData()?.id) { "내 스킬은 null일 수 없습니다." }
            val opponentPokemonId =
                requireNotNull(opponentPokemon.selectedData()?.id) { "상대 포켓몬은 null일 수 없습니다." }

            return battleRepository.calculatedBattlePrediction(
                weatherId = weatherId,
                myPokemonId = myPokemonId,
                mySkillId = mySkillId,
                opponentPokemonId = opponentPokemonId,
            ).toUi()
        }
    }

    private fun initWeathers() {
        viewModelScope.launch(errorHandler) {
            val allWeathers = battleRepository.weathers().map { it.toUi() }
            _weathers.value = allWeathers
        }
    }

    private fun handlePokemonSelection(
        pokemonId: String?,
        selectionType: SelectionType?,
    ) {
        when {
            pokemonId == null -> {
                loadSavedMyPokemon()
                loadSavedOpponentPokemon()
            }

            selectionType == SelectionType.MINE -> {
                selectMyPokemon(pokemonId)
                loadSavedOpponentPokemon()
            }

            selectionType == SelectionType.OPPONENT -> {
                loadSavedMyPokemon()
                selectOpponentPokemon(pokemonId)
            }

            else -> error("선택 타입 정보를 알 수 없습니다.")
        }
    }

    private fun loadSavedMyPokemon() {
        viewModelScope.launch {
            battleRepository.pokemonWithSkillStream().first()?.let { (pokemon, skill) ->
                updateMyPokemon(pokemon.toSelectionUi(), skill.toUi())
            }
        }
    }

    private fun loadSavedOpponentPokemon() {
        viewModelScope.launch {
            battleRepository.pokemonStream().first()?.let {
                updateOpponentPokemon(it.toSelectionUi())
            }
        }
    }

    private fun selectMyPokemon(pokemonId: String) {
        viewModelScope.launch {
            val (pokemon, skill) = battleRepository.pokemonWithSkill(pokemonId)
            val selectionData = SelectionData.WithSkill(pokemon.toSelectionUi(), skill.toUi())
            updatePokemonSelection(selectionData)
        }
    }

    private fun selectOpponentPokemon(pokemonId: String) {
        viewModelScope.launch {
            val pokemon = pokemonRepository.pokemon(pokemonId)
            val selectionData = SelectionData.WithoutSkill(pokemon.toSelectionUi())
            updatePokemonSelection(selectionData)
        }
    }

    fun updateWeather(newWeather: WeatherUiModel) {
        viewModelScope.launch {
            val selectedWeather = BattleSelectionUiState.Selected(newWeather)
            _selectedState.value = selectedState.value.copy(weather = selectedWeather)
            battleRepository.saveWeather(newWeather.id)
        }
    }

    fun updatePokemonSelection(selection: SelectionData) {
        when (selection) {
            is SelectionData.WithSkill -> {
                val (selectedPokemon, selectedSkill) = selection
                updateMyPokemon(selectedPokemon, selectedSkill)
                viewModelScope.launch {
                    battleRepository.saveBattleSelection(selectedPokemon.id, selectedSkill.id)
                }
                logger.logPokemonSkillSelection(selection)
            }

            is SelectionData.WithoutSkill -> {
                val selectedPokemon = selection.selectedPokemon
                updateOpponentPokemon(selectedPokemon)
                viewModelScope.launch {
                    battleRepository.saveBattleSelection(selectedPokemon.id)
                }
                logger.logBattlePokemonSelection(selection)
            }

            is SelectionData.NoSelection -> {}
        }
    }

    private fun updateMyPokemon(
        pokemon: PokemonSelectionUiModel,
        skill: SkillSelectionUiModel,
    ) {
        val selectedPokemon = BattleSelectionUiState.Selected(pokemon)
        val selectedSkill = BattleSelectionUiState.Selected(skill)
        _selectedState.value =
            selectedState.value.copy(minePokemon = selectedPokemon, skill = selectedSkill)
    }

    private fun updateOpponentPokemon(pokemon: PokemonSelectionUiModel) {
        val selectedPokemon = BattleSelectionUiState.Selected(pokemon)
        _selectedState.value = selectedState.value.copy(opponentPokemon = selectedPokemon)
    }

    override fun navigateToSelection(selectionMode: SelectionMode) {
        viewModelScope.launch {
            val selectedPokemon =
                when (selectionMode) {
                    SelectionMode.POKEMON_ONLY -> selectedState.value.opponentPokemon.selectedData()
                    SelectionMode.POKEMON_AND_SKILL, SelectionMode.SKILL_FIRST -> selectedState.value.minePokemon.selectedData()
                }

            val data =
                if (selectedPokemon == null) {
                    SelectionData.NoSelection
                } else {
                    val hasSkillSelection = selectionMode != SelectionMode.POKEMON_ONLY
                    previousSelection(hasSkillSelection, selectedPokemon)
                }

            val navigationData = NavigateToSelection(selectionMode, data)
            _navigationEvent.emit(navigationData)
        }
    }

    private fun previousSelection(
        hasSkillSelection: Boolean,
        previousPokemonSelection: PokemonSelectionUiModel,
    ): SelectionData =
        if (hasSkillSelection) {
            val skill =
                requireNotNull(selectedState.value.skill.selectedData()) { "스킬이 선택되어야 합니다." }
            SelectionData.WithSkill(previousPokemonSelection, skill)
        } else {
            SelectionData.WithoutSkill(previousPokemonSelection)
        }

    override fun navigationToDetail(pokemonId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigateToDetail(pokemonId))
        }
    }
}

sealed interface BattleNavigationEvent

data class NavigateToSelection(
    val selectionMode: SelectionMode,
    val previousSelectionData: SelectionData,
) : BattleNavigationEvent

data class NavigateToDetail(val pokemonId: String) : BattleNavigationEvent
