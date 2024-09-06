package poke.rogue.helper.presentation.battle.selection.skill

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.BattleRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.toUi
import poke.rogue.helper.presentation.battle.selection.QueryHandler
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.dex.filter.toSelectableModelsBy
import poke.rogue.helper.presentation.dex.filter.toSelectableModelsWithAllDeselected
import poke.rogue.helper.stringmatcher.has

class SkillSelectionViewModel(
    private val battleRepository: BattleRepository,
    previousSelection: SelectionData.WithSkill?,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), SkillSelectionHandler, QueryHandler {
    private val _skillSelectedEvent = MutableSharedFlow<SkillSelectionUiModel>()
    val skillSelectedEvent = _skillSelectedEvent.asSharedFlow()

    private val _skills = MutableStateFlow(listOf<SelectableUiModel<SkillSelectionUiModel>>())
    val skills = _skills.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredSkills: StateFlow<List<SelectableUiModel<SkillSelectionUiModel>>> =
        searchQuery
            .debounce(300L)
            .flatMapLatest { query ->
                skills.mapLatest { skillsList ->
                    if (query.isBlank()) {
                        skillsList
                    } else {
                        skillsList.filter { it.data.name.has(query) }
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), skills.value)

    init {
        if (previousSelection != null) {
            viewModelScope.launch(errorHandler) {
                val availableSkills =
                    battleRepository.availableSkills(previousSelection.selectedPokemon.dexNumber)
                        .map { it.toUi() }
                _skills.value =
                    availableSkills.toSelectableModelsBy { it.id == previousSelection.selectedSkill.id }
            }
        }
    }

    override fun selectSkill(selected: SkillSelectionUiModel) {
        _skills.value =
            skills.value.map {
                val isSelected = it.data.id == selected.id
                it.copy(isSelected = isSelected)
            }
        viewModelScope.launch {
            _skillSelectedEvent.emit(selected)
        }
    }

    fun updateSkills(pokemonDexNumber: Long) {
        viewModelScope.launch(errorHandler) {
            _skills.value = emptyList()
            val availableSkills =
                battleRepository.availableSkills(pokemonDexNumber).map { it.toUi() }
            delay(50)
            _skills.value = availableSkills.toSelectableModelsWithAllDeselected()
        }
    }

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }

    companion object {
        fun factory(
            battleRepository: BattleRepository,
            previousSelection: SelectionData.WithSkill?,
        ): ViewModelProvider.Factory = BaseViewModelFactory { SkillSelectionViewModel(battleRepository, previousSelection) }
    }
}
