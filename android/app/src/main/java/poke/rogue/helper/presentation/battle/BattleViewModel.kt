package poke.rogue.helper.presentation.battle

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.error.ErrorViewModel

class BattleViewModel(logger: AnalyticsLogger = analyticsLogger()) : ErrorViewModel(logger) {
    private val _weathers = MutableStateFlow(WeatherUiModel.DUMMY)
    val weathers = _weathers.asStateFlow()

    private val _selectedWeather = MutableStateFlow(WeatherUiModel.DEFAULT_SELECTED)
    val selectedWeather = _selectedWeather.asStateFlow()

    fun updateWeather(newWeather: WeatherUiModel) {
        viewModelScope.launch {
            _selectedWeather.emit(newWeather)
        }
    }
}
