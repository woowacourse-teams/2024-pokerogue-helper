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
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class PokemonDetailViewModel(
    private val dexRepository: DexRepository,
    private val biomeRepository: BiomeRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) :
    ErrorHandleViewModel(logger),
    PokemonDetailNavigateHandler {
    private val _uiState: MutableStateFlow<PokemonDetailUiState> =
        MutableStateFlow(PokemonDetailUiState.IsLoading)
    val uiState = _uiState.asStateFlow()

    private val _uiState2: MutableStateFlow<PokemonDetailUiState2> = MutableStateFlow(PokemonDetailUiState2.IsLoading)
    val uiState2 = _uiState2.asStateFlow()

    val isEmpty2: StateFlow<Boolean> = uiState2.map { it is PokemonDetailUiState2.IsLoading }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    val isEmpty: StateFlow<Boolean> =
        uiState.map { it is PokemonDetailUiState.IsLoading }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), true)

    private val _navigationToAbilityDetailEvent = MutableSharedFlow<String>()
    val navigationToAbilityDetailEvent: SharedFlow<String> = _navigationToAbilityDetailEvent.asSharedFlow()

    private val _navigationToBiomeDetailEvent = MutableSharedFlow<String>()
    val navigationToBiomeDetailEvent: SharedFlow<String> = _navigationToBiomeDetailEvent.asSharedFlow()

    private val _navigateToHomeEvent = MutableSharedFlow<Boolean>()
    val navigateToHomeEvent = _navigateToHomeEvent.asSharedFlow()

    private val _navigateToPokemonDetailEvent = MutableSharedFlow<String>()
    val navigateToPokemonDetailEvent = _navigateToPokemonDetailEvent.asSharedFlow()

    fun updatePokemonDetail(pokemonId: String?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        viewModelScope.launch {
            _uiState.value = dexRepository.pokemonDetail(pokemonId).toUi()
        }

        viewModelScope.launch {
            val pd = dexRepository.pokemonDetail(pokemonId)
            val biomes = biomeRepository.biomes()

            _uiState2.value = pd.toUi(biomes)
        }
    }

    override fun navigateToAbilityDetail(abilityId: String) {
        viewModelScope.launch {
            _navigationToAbilityDetailEvent.emit(abilityId)
        }
    }

    override fun navigateToBiomeDetail(biomeId: String) {
        viewModelScope.launch {
            _navigationToBiomeDetailEvent.emit(biomeId)
        }
    }

    override fun navigateToHome() {
        viewModelScope.launch {
            _navigateToHomeEvent.emit(true)
        }
    }

    override fun navigateToPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            _navigateToPokemonDetailEvent.emit(pokemonId)
        }
    }

    companion object {
        fun factory(
            dexRepository: DexRepository,
            biomeRepository: BiomeRepository,
        ): ViewModelProvider.Factory = BaseViewModelFactory { PokemonDetailViewModel(dexRepository, biomeRepository) }
    }
}
