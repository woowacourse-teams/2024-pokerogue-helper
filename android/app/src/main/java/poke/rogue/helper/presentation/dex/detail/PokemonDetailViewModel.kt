package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.logPokemonDetailToBattle
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow

class PokemonDetailViewModel(
    private val dexRepository: DexRepository,
    private val logger: AnalyticsLogger = analyticsLogger(),
) :
    ErrorHandleViewModel(logger),
        PokemonDetailNavigateHandler {
    private val _uiState: MutableStateFlow<PokemonDetailUiState> =
        MutableStateFlow(PokemonDetailUiState.IsLoading)
    val uiState = _uiState.asStateFlow()

    val isLoading: StateFlow<Boolean> =
        uiState.map { it is PokemonDetailUiState.IsLoading }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    private val _navigationToAbilityDetailEvent = MutableEventFlow<String>()
    val navigationToAbilityDetailEvent = _navigationToAbilityDetailEvent.asEventFlow()

    private val _navigationToBiomeDetailEvent = MutableEventFlow<String>()
    val navigationToBiomeDetailEvent = _navigationToBiomeDetailEvent.asEventFlow()

    private val _navigateToHomeEvent = MutableEventFlow<Boolean>()
    val navigateToHomeEvent = _navigateToHomeEvent.asEventFlow()

    private val _navigateToBattleEvent = MutableEventFlow<NavigateToBattleEvent>()
    val navigateToBattleEvent = _navigateToBattleEvent.asEventFlow()

    private val _navigationEvent = MutableEventFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asEventFlow()

    private val _evolutionsEvent = MutableEventFlow<PokemonEvolutionEvent>()
    val evolutionsEvent = _evolutionsEvent.asEventFlow()

    fun updatePokemonDetail(pokemonId: String?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        viewModelScope.launch {
            _uiState.value = dexRepository.pokemonDetail(pokemonId).toUi()
        }
    }

    override fun navigateToAbilityDetail(abilityId: String) {
        viewModelScope.launch {
            // TODO: REMOVE
            _navigationToAbilityDetailEvent.emit(abilityId)
            _navigationEvent.emit(NavigationEvent.ToAbilityDetail(abilityId))
        }
    }

    override fun navigateToBiomeDetail(biomeId: String) {
        viewModelScope.launch {
            // TODO: REMOVE
            _navigationToBiomeDetailEvent.emit(biomeId)
            _navigationEvent.emit(NavigationEvent.ToBiomeDetail(biomeId))
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            // TODO: REMOVE
            _navigateToHomeEvent.emit(true)
            _navigationEvent.emit(NavigationEvent.ToHome)
        }
    }

    override fun navigateToPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            pokemonUiModel().let { pokemon ->
                if (pokemon.id == pokemonId) {
                    _evolutionsEvent.emit(PokemonEvolutionEvent.SameWithCurrentPokemon(pokemon.name))
                    return@launch
                }
                _evolutionsEvent.emit(PokemonEvolutionEvent.NavigateToPokemonDetail(pokemonId))
            }
        }
    }

    override fun navigateToBattleWithMine() {
        viewModelScope.launch {
            val navigation = NavigateToBattleEvent.WithMyPokemon(pokemonUiModel())
            _navigateToBattleEvent.emit(navigation)
            logger.logPokemonDetailToBattle(navigation)
        }
    }

    override fun navigateToBattleWithOpponent() {
        viewModelScope.launch {
            val navigation = NavigateToBattleEvent.WithOpponentPokemon(pokemonUiModel())
            _navigateToBattleEvent.emit(navigation)
            logger.logPokemonDetailToBattle(navigation)
        }
    }

    private suspend fun pokemonUiModel() =
        uiState
            .filterIsInstance<PokemonDetailUiState.Success>()
            .first().pokemon

    sealed interface NavigationEvent {
        data class ToAbilityDetail(val id: String) : NavigationEvent

        data class ToBiomeDetail(val id: String) : NavigationEvent

        data object ToHome : NavigationEvent

        data object NONE : NavigationEvent
    }
}
