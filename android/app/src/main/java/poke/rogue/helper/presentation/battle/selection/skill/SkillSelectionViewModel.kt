package poke.rogue.helper.presentation.battle.selection.skill

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.SelectionData
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.SelectableUiModel

class SkillSelectionViewModel(
    previousSelection: SelectionData.WithSkill?,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), SkillSelectionHandler {
    private val _skillSelectedEvent = MutableSharedFlow<SkillSelectionUiModel>()
    val skillSelectedEvent = _skillSelectedEvent.asSharedFlow()

    private val _skills = MutableStateFlow(listOf<SelectableUiModel<SkillSelectionUiModel>>())
    val skills = _skills.asStateFlow()

    init {
        // TODO: 기존에 선택한 포켓몬 dex Number로 서버 요청
        _skills.value =
            SkillSelectionUiModel.DUMMY.mapIndexed { index, skill ->
                SelectableUiModel(index, previousSelection?.selectedSkill?.id == skill.id, skill)
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
        // TODO: dex number로 레포지토리에 요청
        _skills.value =
            SkillSelectionUiModel.DUMMY.mapIndexed { index, skill ->
                SelectableUiModel(index, false, skill)
            }
    }

    companion object {
        fun factory(previousSelection: SelectionData.WithSkill?): ViewModelProvider.Factory =
            BaseViewModelFactory { SkillSelectionViewModel(previousSelection) }
    }
}
