package poke.rogue.helper.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AbilityViewModel : ViewModel(), AbilityActionHandler {
    private val _navigationEvent = MutableSharedFlow<Long>()
    val navigationEvent: SharedFlow<Long> = _navigationEvent.asSharedFlow()

    override fun navigateToDetail(abilityId: Long) {
        viewModelScope.launch {
            _navigationEvent.emit(abilityId)
        }
    }
}
