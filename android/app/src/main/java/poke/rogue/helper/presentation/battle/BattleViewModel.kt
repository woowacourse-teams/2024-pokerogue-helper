package poke.rogue.helper.presentation.battle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.battle.model.WeatherUiModel
import poke.rogue.helper.presentation.error.ErrorViewModel

class BattleViewModel(logger: AnalyticsLogger = analyticsLogger()) : ErrorViewModel(logger) {
    private val _weather = MutableStateFlow(WeatherUiModel.DEFAULT_SELECTED)
    val weather = _weather.asStateFlow()

    fun updateWeather(newWeather: WeatherUiModel) {
        _weather.value = newWeather
    }
}
