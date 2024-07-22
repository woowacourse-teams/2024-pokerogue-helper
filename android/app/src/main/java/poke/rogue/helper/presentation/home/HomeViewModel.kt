package poke.rogue.helper.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.presentation.util.Event

class HomeViewModel : ViewModel(), HomeActionHandler {
    private val _navigateHandler = MutableSharedFlow<Event<HomeNavigateUiState>>()
    val navigateHandler: SharedFlow<Event<HomeNavigateUiState>> = _navigateHandler.asSharedFlow()

    override fun onClickType() {
        viewModelScope.launch {
            _navigateHandler.emit(Event(HomeNavigateUiState.ToType))
        }
    }

    override fun onClickDex() {
        viewModelScope.launch {
            _navigateHandler.emit(Event(HomeNavigateUiState.ToDex))
        }
    }

    override fun onClickAbility() {
        viewModelScope.launch {
            _navigateHandler.emit(Event(HomeNavigateUiState.ToAbility))
        }
    }

    override fun onClickTip() {
        viewModelScope.launch {
            _navigateHandler.emit(Event(HomeNavigateUiState.ToTip))
        }
    }

    override fun onClickLogo() {
        viewModelScope.launch {
            _navigateHandler.emit(Event(HomeNavigateUiState.ToLogo))
        }
    }
}
