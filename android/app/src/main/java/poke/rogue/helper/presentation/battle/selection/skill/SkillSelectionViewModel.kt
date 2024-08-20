package poke.rogue.helper.presentation.battle.selection.skill

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.SelectableUiModel

class SkillSelectionViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger), SkillSelectionHandler {
    private val _skills =
        MutableStateFlow(
            SkillSelectionUiModel.DUMMY.mapIndexed { index, skill ->
                SelectableUiModel(index, false, skill)
            },
        )
    val skills = _skills.asStateFlow()

    private val _skillSelectedEvent = MutableSharedFlow<SkillSelectionUiModel>()
    val skillSelectedEvent = _skillSelectedEvent.asSharedFlow()

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
}
