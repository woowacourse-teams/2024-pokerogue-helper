package poke.rogue.helper.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(), HomeActionHandler {
    private val _navigationEvent = MutableSharedFlow<HomeNavigateEvent>()
    val navigationEvent: SharedFlow<HomeNavigateEvent> = _navigationEvent.asSharedFlow()

    override fun navigateToType() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigateEvent.ToType)
        }
    }

    override fun navigateToDex() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigateEvent.ToDex)
        }
    }

    override fun navigateToAbility() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigateEvent.ToAbility)
        }
    }

    override fun navigateToTip() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigateEvent.ToTip)
        }
    }

    override fun navigateToPokeRogue() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigateEvent.ToLogo)
        }
    }
}
