package poke.rogue.helper.presentation.battle

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.WeatherUiModel

class BattleViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger),
    BattleNavigationHandler {
    private val _weathers = MutableStateFlow(WeatherUiModel.DUMMY)
    val weathers = _weathers.asStateFlow()

    private val _selectedState = MutableStateFlow(BattleSelectionsState.DEFAULT)
    val selectedState = _selectedState.asStateFlow()

    private val _navigateToSelection = MutableSharedFlow<Boolean>()
    val navigateToSelection = _navigateToSelection.asSharedFlow()

    fun updateWeather(newWeather: WeatherUiModel) {
        viewModelScope.launch {
            _selectedState.value = selectedState.value.copy(weather = newWeather)
        }
    }

    override fun navigateToSelection(hasSkillSelection: Boolean) {
        viewModelScope.launch {
            _navigateToSelection.emit(hasSkillSelection)
        }
    }
}
