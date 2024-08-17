package poke.rogue.helper.presentation.biome

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class BiomeViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger),
    BiomeUiEventHandler {
    private val _navigationToDetailEvent = MutableSharedFlow<String>()
    val navigationToDetailEvent: SharedFlow<String> = _navigationToDetailEvent.asSharedFlow()

    override fun navigateToDetail(biomeId: String) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(biomeId)
        }
    }
}
