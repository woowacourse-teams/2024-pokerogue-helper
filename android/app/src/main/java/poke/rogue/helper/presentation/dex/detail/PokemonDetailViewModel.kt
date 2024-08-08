package poke.rogue.helper.presentation.dex.detail

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
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.error.ErrorViewModel

class PokemonDetailViewModel(
    private val dexRepository: DexRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) :
    ErrorViewModel(logger),
        PokemonDetailNavigateHandler {
    private val _uiState: MutableStateFlow<PokemonDetailUiState> =
        MutableStateFlow(PokemonDetailUiState.IsLoading)
    val uiState = _uiState.asStateFlow()

    val isEmpty: StateFlow<Boolean> =
        uiState.map { it is PokemonDetailUiState.IsLoading }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    private val _navigationToDetailEvent = MutableSharedFlow<Long>()
    val navigationToDetailEvent: SharedFlow<Long> = _navigationToDetailEvent.asSharedFlow()

    private val _navigateToHomeEvent = MutableSharedFlow<Boolean>()
    val navigateToHomeEvent = _navigateToHomeEvent.asSharedFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        viewModelScope.launch(errorHandler) {
            _uiState.value = dexRepository.pokemonDetail(pokemonId).toUi()
        }
    }

    override fun navigateToAbilityDetail(abilityId: Long) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(abilityId)
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            _navigateToHomeEvent.emit(true)
        }
    }

    companion object {
        fun factory(dexRepository: DexRepository): ViewModelProvider.Factory =
            BaseViewModelFactory { PokemonDetailViewModel(dexRepository) }
    }
}
