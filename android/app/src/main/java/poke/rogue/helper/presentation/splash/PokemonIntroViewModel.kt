package poke.rogue.helper.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.koin.mp.KoinPlatform.getKoin
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow

class PokemonIntroViewModel(
    private val pokemonRepository: DexRepository,
    logger: AnalyticsLogger,
) : ErrorHandleViewModel(logger) {
    private val _navigationToHomeEvent = MutableEventFlow<Unit>()
    val navigationToHomeEvent = _navigationToHomeEvent.asEventFlow()

    init {
        refreshEvent
            .onStart { emit(Unit) }
            .onEach {
                try {
                    coroutineScope {
                        launch { delay(MIN_SPLASH_TIME) }
                        launch { pokemonRepository.warmUp() }
                        // TODO Koin 일괄적으로 적용 시 삭제 예정
                        launch { getKoin().get<DexRepository>().warmUp() }
                    }
                } catch (e: Exception) {
                    handlePokemonError(e)
                } finally {
                    _navigationToHomeEvent.emit(Unit)
                }
            }
            .launchIn(viewModelScope + errorHandler)
    }

    companion object {
        private const val MIN_SPLASH_TIME = 1000L

        fun factory(
            pokemonRepository: DexRepository,
            logger: AnalyticsLogger,
        ) = BaseViewModelFactory {
            PokemonIntroViewModel(pokemonRepository, logger)
        }
    }
}
