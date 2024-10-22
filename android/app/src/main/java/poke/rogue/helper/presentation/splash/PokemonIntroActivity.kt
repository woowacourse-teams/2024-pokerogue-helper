package poke.rogue.helper.presentation.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import poke.rogue.helper.R
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonIntroBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonIntroActivity :
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
                finishAffinity()
                startActivity<HomeActivity>()
            }
        }
    }
}
