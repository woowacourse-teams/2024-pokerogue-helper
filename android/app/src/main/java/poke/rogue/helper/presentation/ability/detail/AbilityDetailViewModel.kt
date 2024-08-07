package poke.rogue.helper.presentation.ability.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.ability.model.AbilityDetailUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class AbilityDetailViewModel(private val abilityRepository: AbilityRepository) :
    ViewModel(), AbilityDetailUiEventHandler {
    private val _abilityDetail =
        MutableStateFlow<AbilityDetailUiState<AbilityDetailUiModel>>(AbilityDetailUiState.Loading)
    val abilityDetail = _abilityDetail.asStateFlow()

    private val _navigationToPokemonDetailEvent = MutableSharedFlow<Long>()
    val navigationToPokemonDetailEvent: SharedFlow<Long> =
        _navigationToPokemonDetailEvent.asSharedFlow()

    private val _navigateToHomeEvent = MutableSharedFlow<Boolean>()
    val navigateToHomeEvent: SharedFlow<Boolean> = _navigateToHomeEvent.asSharedFlow()

    private val _errorEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val errorEvent = _errorEvent.asSharedFlow()

    override fun navigateToPokemonDetail(pokemonId: Long) {
        viewModelScope.launch {
            _navigationToPokemonDetailEvent.emit(pokemonId)
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            _navigateToHomeEvent.emit(true)
        }
    }

    fun updateAbilityDetail(abilityId: Long) {
        if (abilityId == -1L) {
            _errorEvent.tryEmit(Unit)
            return
        }
        viewModelScope.launch {
            val abilityDetail = abilityRepository.abilityDetail(abilityId).toUi()
            _abilityDetail.value = AbilityDetailUiState.Success(abilityDetail)
        }
    }

    companion object {
        fun factory(abilityRepository: AbilityRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AbilityDetailViewModel(abilityRepository)
            }
    }
}
