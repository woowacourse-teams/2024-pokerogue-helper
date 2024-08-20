package poke.rogue.helper.presentation.biome

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        BiomeUiEventHandler {
    private val _biome = MutableStateFlow<BiomeUiState<List<Biome>>>(BiomeUiState.Loading)
    val biome = _biome.asStateFlow()

    private val _navigationToDetailEvent = MutableSharedFlow<String>()
    val navigationToDetailEvent: SharedFlow<String> = _navigationToDetailEvent.asSharedFlow()

    init {
        updateBiomes()
    }

    private fun updateBiomes() {
        viewModelScope.launch(errorHandler) {
            val biomes = biomeRepository.biomes()
            _biome.value = BiomeUiState.Success(biomes)
        }
    }

    override fun navigateToDetail(biomeId: String) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(biomeId)
        }
    }

    companion object {
        fun factory(biomeRepository: BiomeRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                BiomeViewModel(biomeRepository)
            }
    }
}
