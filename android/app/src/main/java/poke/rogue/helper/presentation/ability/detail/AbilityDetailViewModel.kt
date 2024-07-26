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
import poke.rogue.helper.data.repository.AbilityDetailRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory

class AbilityDetailViewModel(private val abilityDetailRepository: AbilityDetailRepository) :
    ViewModel(), AbilityDetailUiEventHandler {
    private val _abilityDetail =
        MutableStateFlow<AbilityDetailUiState<AbilityDetailUiModel>>(AbilityDetailUiState.Loading)
    val abilityDetail = _abilityDetail.asStateFlow()

    private val _navigationToPokemonDetailEvent = MutableSharedFlow<Long>()
    val navigationToPokemonDetailEvent: SharedFlow<Long> =
        _navigationToPokemonDetailEvent.asSharedFlow()

    override fun navigateToPokemonDetail(pokemonId: Long) {
        viewModelScope.launch {
            _navigationToPokemonDetailEvent.emit(pokemonId)
        }
    }

    suspend fun updateAbilityDetail(abilityId: Long) {
        viewModelScope.launch {
            val abilityDetail = abilityDetailRepository.abilityDetail(abilityId).toUi()
            _abilityDetail.value = AbilityDetailUiState.Success(abilityDetail)
        }
    }

    companion object {
        fun factory(abilityDetailRepository: AbilityDetailRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AbilityDetailViewModel(abilityDetailRepository)
            }
    }
}
