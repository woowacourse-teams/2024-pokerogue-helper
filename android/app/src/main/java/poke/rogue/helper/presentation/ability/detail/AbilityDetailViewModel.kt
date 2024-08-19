package poke.rogue.helper.presentation.ability.detail

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.ability.model.AbilityDetailUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class AbilityDetailViewModel(
    private val abilityRepository: AbilityRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger), AbilityDetailUiEventHandler {
    private val _abilityDetail =
        MutableStateFlow<AbilityDetailUiState<AbilityDetailUiModel>>(AbilityDetailUiState.Loading)
    val abilityDetail = _abilityDetail.asStateFlow()

    private val _navigationToPokemonDetailEvent = MutableSharedFlow<String>()
    val navigationToPokemonDetailEvent: SharedFlow<String> =
        _navigationToPokemonDetailEvent.asSharedFlow()

    private val _navigateToHomeEvent = MutableSharedFlow<Boolean>()
    val navigateToHomeEvent: SharedFlow<Boolean> = _navigateToHomeEvent.asSharedFlow()

    private val _errorEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val errorEvent = _errorEvent.asSharedFlow()

    val isEmpty: StateFlow<Boolean> =
        abilityDetail.map { it is AbilityDetailUiState.Success && it.data.pokemons.isEmpty() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    override fun navigateToPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            _navigationToPokemonDetailEvent.emit(pokemonId)
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            _navigateToHomeEvent.emit(true)
        }
    }

    fun updateAbilityDetail(abilityId: String) {
        if (abilityId.isBlank()) {
            _errorEvent.tryEmit(Unit)
            return
        }
        viewModelScope.launch(errorHandler) {
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
