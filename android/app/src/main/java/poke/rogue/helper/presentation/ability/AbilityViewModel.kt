package poke.rogue.helper.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class AbilityViewModel(private val abilityRepository: AbilityRepository) : ViewModel(),
    AbilityUiEventHandler {
    private val _navigationToDetailEvent = MutableSharedFlow<Long>()
    val navigationToDetailEvent: SharedFlow<Long> = _navigationToDetailEvent.asSharedFlow()

    override fun navigateToDetail(abilityId: Long) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(abilityId)
        }
    }

    companion object {
        fun factory(abilityRepository: AbilityRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AbilityViewModel(abilityRepository)
            }
    }
}
