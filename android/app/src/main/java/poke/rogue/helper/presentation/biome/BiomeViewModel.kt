package poke.rogue.helper.presentation.biome

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.repository.BiomeRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class BiomeViewModel(
    private val biomeRepository: BiomeRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) :
    ErrorHandleViewModel(logger),
    BiomeUiEventHandler, BiomeQueryHandler {

    private val _navigationToDetailEvent = MutableSharedFlow<String>()
    val navigationToDetailEvent: SharedFlow<String> = _navigationToDetailEvent.asSharedFlow()

    private val _navigateToGuideEvent = MutableSharedFlow<Unit>()
    val navigateToGuideEvent: SharedFlow<Unit> = _navigateToGuideEvent.asSharedFlow()

    val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val biomes: StateFlow<BiomeUiState<List<Biome>>> =
        searchQuery
            .debounce(300L)
            .mapLatest { query ->
                val biomes = biomeRepository.biomes(query)
                BiomeUiState.Success(biomes)
            }
            .stateIn(
                viewModelScope + errorHandler,
                SharingStarted.WhileSubscribed(5000L),
                BiomeUiState.Loading,
            )

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }

    override fun navigateToDetail(biomeId: String) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(biomeId)
        }
    }

    override fun navigateToGuide() {
        viewModelScope.launch {
            _navigateToGuideEvent.emit(Unit)
        }
    }

    companion object {
        fun factory(biomeRepository: BiomeRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                BiomeViewModel(biomeRepository)
            }
    }
}
