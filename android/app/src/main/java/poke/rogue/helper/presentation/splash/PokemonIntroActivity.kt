package poke.rogue.helper.presentation.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.databinding.ActivityPokemonIntroBinding
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.event.MutableEventFlow
import poke.rogue.helper.presentation.util.event.asEventFlow
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonIntroActivity() :
    ErrorHandleActivity<ActivityPokemonIntroBinding>(R.layout.activity_pokemon_intro) {
    private val viewModel by viewModels<PokemonIntroViewModel> {
        PokemonIntroViewModel.factory(DefaultDexRepository.instance(), analyticsLogger())
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel
    override val toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.navigationToHomeEvent.collect {
                startActivity<HomeActivity>()
            }
        }
    }
}

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
                coroutineScope {
                    val warmUp = async { pokemonRepository.warmUp() }
                    val delay = async { delay(1000) }
                    listOf(warmUp, delay).awaitAll()
                }
                _navigationToHomeEvent.emit(Unit)
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
