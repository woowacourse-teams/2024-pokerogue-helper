package poke.rogue.helper.presentation.biome.detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow
import timber.log.Timber

class BiomeDetailViewModel(
    private val biomeRepository: BiomeRepository,
    analytics: AnalyticsLogger,
) : ErrorHandleViewModel(analytics), BiomeDetailHandler, PokemonListNavigateHandler {
    private val biomeId: MutableStateFlow<String> = MutableStateFlow(IDLE_ID)

    // TODO : 아직 작업 다 안끝났음
    val uiState: StateFlow<BiomeDetailUiState> =
        combine(
            biomeId,
            refreshEvent.onStart { emit(Unit) },
        ) { id, _ ->
            Timber.d("combine - biomeId: $id")
            id
        }.filter { id ->
            Timber.d("filter - biomeId: $id")
            id != IDLE_ID
        }.map { id ->
            Timber.d("map - biomeId: $id")
            biomeRepository.biomeDetail(id).toUiState()
        }.stateIn(
            viewModelScope + errorHandler,
            SharingStarted.WhileSubscribed(5000),
            BiomeDetailUiState.Default,
        )

    private val _uiEvent = MutableEventFlow<BiomeDetailUiEvent>()
    val uiEvent = _uiEvent.asEventFlow()

    val isLoading: StateFlow<Boolean> =
        uiState.map {
            it == BiomeDetailUiState.Default
        }.stateIn(
            viewModelScope + errorHandler,
            SharingStarted.WhileSubscribed(5000),
            true,
        )

    fun init(id: String) {
        if (id.isBlank()) return handlePokemonError(IllegalArgumentException("biomeId is blank"))
        biomeId.value = id
    }

    override fun navigateToBiomeDetail(id: String) {
        viewModelScope.launch {
            _uiEvent.emit(BiomeDetailUiEvent.NavigateToNextBiomeDetail(id))
        }
    }

    override fun navigateToPokemonDetail(id: String) {
        viewModelScope.launch {
            _uiEvent.emit(BiomeDetailUiEvent.NavigateToPokemonDetail(id))
        }
    }

    companion object {
        private const val IDLE_ID = "IDLE"

        fun factory(
            biomeRepository: BiomeRepository,
            analytics: AnalyticsLogger,
        ) = BaseViewModelFactory {
            BiomeDetailViewModel(biomeRepository, analytics)
        }
    }
}

sealed interface BiomeDetailUiEvent {
    data class NavigateToNextBiomeDetail(val biomeId: String) : BiomeDetailUiEvent

    data class NavigateToPokemonDetail(val pokemonId: String) : BiomeDetailUiEvent
}

interface BiomeDetailHandler {
    fun navigateToBiomeDetail(id: String)
}
