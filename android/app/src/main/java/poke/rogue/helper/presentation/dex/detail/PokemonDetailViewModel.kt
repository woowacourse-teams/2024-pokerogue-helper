package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.logPokemonDetailToBattle
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
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

    private val successUiState: PokemonDetailUiState.Success? get() = uiState.value as? PokemonDetailUiState.Success

    val isLoading: StateFlow<Boolean> =
        uiState.map { it is PokemonDetailUiState.IsLoading }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    private val _navigationEvent = MutableEventFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asEventFlow()

    fun updatePokemonDetail(pokemonId: String?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        viewModelScope.launch {
            _uiState.value = dexRepository.pokemonDetail(pokemonId).toUi()
        }
    }

    override fun navigateToPokemonList() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ToPokemonList)
        }
    }

    override fun navigateToAbilityDetail(abilityId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ToAbilityDetail(abilityId))
        }
    }

    override fun navigateToBiomeDetail(biomeId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ToBiomeDetail(biomeId))
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ToHome)
        }
    }

    override fun navigateToPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            successUiState?.let { state ->
                if (state.pokemon.id == pokemonId) {
                    _navigationEvent.emit(NavigationEvent.ToPokemonDetail.Failure(state.pokemon.name))
                    return@launch
                }
                _navigationEvent.emit(NavigationEvent.ToPokemonDetail.Success(pokemonId))
            }
        }
    }

    override fun navigateToBattleWithMine() {
        viewModelScope.launch {
            successUiState?.let { state ->
                val event = NavigationEvent.ToBattle.WithMyPokemon(state.pokemon)
                _navigationEvent.emit(event)
                logger.logPokemonDetailToBattle(event)
            }
        }
    }

    override fun navigateToBattleWithOpponent() {
        viewModelScope.launch {
            successUiState?.let { state ->
                val event = NavigationEvent.ToBattle.WithOpponentPokemon(state.pokemon)
                _navigationEvent.emit(event)
                logger.logPokemonDetailToBattle(event)
            }
        }
    }

    sealed interface NavigationEvent {
        data object ToPokemonList : NavigationEvent

        data class ToAbilityDetail(val id: String) : NavigationEvent

        data class ToBiomeDetail(val id: String) : NavigationEvent

        data object ToHome : NavigationEvent

        data object None : NavigationEvent

        sealed class ToBattle : NavigationEvent {
            data class WithMyPokemon(val pokemon: PokemonUiModel) : ToBattle()

            data class WithOpponentPokemon(val pokemon: PokemonUiModel) : ToBattle()
        }

        sealed class ToPokemonDetail : NavigationEvent {
            data class Success(val pokemonId: String) : ToPokemonDetail()

            data class Failure(val pokemonName: String) : ToPokemonDetail()
        }
    }
}
