package poke.rogue.helper.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(), HomeActionHandler {
    private val _navigateHandler = MutableSharedFlow<HomeNavigateState>()
    val navigateHandler: SharedFlow<HomeNavigateState> = _navigateHandler.asSharedFlow()

    override fun onClickType() {
        viewModelScope.launch {
            _navigateHandler.emit(HomeNavigateState.ToType)
        }
    }

    override fun onClickDex() {
        viewModelScope.launch {
            _navigateHandler.emit(HomeNavigateState.ToDex)
        }
    }

    override fun onClickAbility() {
        viewModelScope.launch {
            _navigateHandler.emit(HomeNavigateState.ToAbility)
        }
    }

    override fun onClickTip() {
        viewModelScope.launch {
            _navigateHandler.emit(HomeNavigateState.ToTip)
        }
    }

    override fun onClickLogo() {
        viewModelScope.launch {
            _navigateHandler.emit(HomeNavigateState.ToLogo)
        }
    }
}
