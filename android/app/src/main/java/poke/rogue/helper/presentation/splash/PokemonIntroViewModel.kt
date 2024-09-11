package poke.rogue.helper.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus
import kotlinx.coroutines.supervisorScope
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
                    supervisorScope {
                        val warmUp = async { pokemonRepository.warmUp() }
                        delay(1000)
                        warmUp.await()
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
        fun factory(
            pokemonRepository: DexRepository,
            logger: AnalyticsLogger,
        ) = BaseViewModelFactory {
            PokemonIntroViewModel(pokemonRepository, logger)
        }
    }
}
