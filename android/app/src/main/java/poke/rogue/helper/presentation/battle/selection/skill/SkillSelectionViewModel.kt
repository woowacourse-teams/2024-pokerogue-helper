package poke.rogue.helper.presentation.battle.selection.skill

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.SelectableUiModel

class SkillSelectionViewModel(
    previousSelection: SkillSelectionUiModel?,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), SkillSelectionHandler {
    private val _skillSelectedEvent = MutableSharedFlow<SkillSelectionUiModel>()
    val skillSelectedEvent = _skillSelectedEvent.asSharedFlow()

    private val _skills: MutableStateFlow<List<SelectableUiModel<SkillSelectionUiModel>>>
    val skills: StateFlow<List<SelectableUiModel<SkillSelectionUiModel>>>

    init {
        _skills =
            MutableStateFlow(
                SkillSelectionUiModel.DUMMY.mapIndexed { index, skill ->
                    SelectableUiModel(index, previousSelection?.id == skill.id, skill)
                },
            )
        skills = _skills.asStateFlow()
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

    companion object {
        fun factory(previousSelection: SkillSelectionUiModel?): ViewModelProvider.Factory =
            BaseViewModelFactory { SkillSelectionViewModel(previousSelection) }
    }
}
