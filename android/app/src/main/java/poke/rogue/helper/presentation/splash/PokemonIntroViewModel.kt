package poke.rogue.helper.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow
import timber.log.Timber

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
                        val warmUp = async { pokemonRepository.warmUp() }
                        val delay = async { delay(MIN_SPLASH_TIME) }
                        awaitAll(warmUp, delay)
                        pokemonRepository.warmUp()
                    }
                    _navigationToHomeEvent.emit(Unit)
                } catch (e: Exception) {
                    Timber.e(e)
                    handlePokemonError(e)
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
